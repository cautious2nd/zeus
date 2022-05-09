/**
 * @Title:ClassDAOXmlUtil.java
 * @author:Riozenc
 * @datetime:2015年6月9日 上午9:15:05
 */
package org.scaffold.mybatis.generator;


import org.scaffold.mybatis.generator.util.file.FileUtil;
import org.scaffold.mybatis.generator.util.reflect.ReflectUtil;
import org.scaffold.mybatis.generator.util.string.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ClassDAOXmlUtil {

	public static String getInsert(Class<?> clazz) {
		StringBuffer sb = new StringBuffer();

		// Field[] fields = clazz.getDeclaredFields();
		Field[] fields = ReflectUtil.getFields(clazz);

		sb.append("(");
		for (Field field : fields) {
			sb.append("#{" + field.getName() + "},");
		}
		sb.setLength(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}

	public static String getInsertBatch(Class<?> clazz) {
		StringBuffer sb = new StringBuffer();

		// Field[] fields = clazz.getDeclaredFields();
		Field[] fields = ReflectUtil.getFields(clazz);

		sb.append("(");
		for (Field field : fields) {
			sb.append("#{item." + field.getName() + "},");
		}
		sb.setLength(sb.length() - 1);
		sb.append(")");
		return sb.toString();
	}


	public static String getUpdate(Class<?> clazz) {
		StringBuffer sb = new StringBuffer();

		// Field[] fields = clazz.getDeclaredFields();
		Field[] fields = ReflectUtil.getFields(clazz);

		for (Field field : fields) {
			if (field.getAnnotation(GeneratorTablePrimaryKey.class) != null) {
				continue;
			}
			sb.append("<if test=\"" + field.getName() + " !=null\">").append("\n");
			sb.append(StringUtils.allToUpper(field.getName()) + " = #{" + field.getName() + "},").append("\n");
			sb.append("</if>").append("\n");
		}
		return sb.toString();
	}

	public static String getUpdateBatch(Class<?> clazz) throws Exception{
		StringBuffer sb = new StringBuffer();

		// Field[] fields = clazz.getDeclaredFields();
		Field[] fields = ReflectUtil.getFields(clazz);

		for (Field field : fields) {
			if (field.getAnnotation(GeneratorTablePrimaryKey.class) != null) {
				continue;
			}
			sb.append("<trim prefix=\""+StringUtils.allToUpper(field.getName())+" =case\" suffix=\"end,\">").append("\n");
			sb.append("<foreach collection=\"list\" item=\"i\" index=\"index\">");
			sb.append("<if test=\"i." + field.getName() + " !=null\">").append("\n");

			List<String> primaryKeys = getPrimaryKeys(clazz);
			if (0 != primaryKeys.size()) {
				sb.append("when");
				sb.append("\n");
				for(int i=0;i<getPrimaryKeys(clazz).size();i++){
					if(i==0){
						sb.append(dynamicUpdateBatchSqlFormat(getPrimaryKeys(clazz).get(i), false,
								false));
					}else{
						sb.append(" and ");
						sb.append(dynamicUpdateBatchSqlFormat(getPrimaryKeys(clazz).get(i), true,
								false));
					}
				}
			} else {
				throw new Exception("生成update语句无主键,存在隐患....");
			}


			sb.append("then #{i."+field.getName()+"}").append("\n");
			sb.append("</if>").append("\n");
			sb.append("</foreach>").append("\n");
			sb.append("</trim>").append("\n");
		}
		return sb.toString();
	}

	public static String getColumns(Class<?> clazz) {
		StringBuffer sb = new StringBuffer();
		// Field[] fields = clazz.getDeclaredFields();
		Field[] fields = ReflectUtil.getFields(clazz);

		for (Field field : fields) {

			sb.append(StringUtils.allToUpper(field.getName())).append(",");
		}
		sb.setLength(sb.length() - 1);
		return sb.toString();
	}

	public static List<String> getPrimaryKeys(Class<?> clazz) {
		List<String> list = new ArrayList<String>();

		Field[] fields = ReflectUtil.getFields(clazz);

		Object value = null;
		for (Field field : fields) {
			value = field.getAnnotation(GeneratorTablePrimaryKey.class);
			if (null != value) {
				list.add(field.getName());
			}
		}
		return list;
	}

	private static String dynamicSqlFormat(String fieldName, boolean isAnd, boolean isIf) {
		if (isIf) {
			return dynamicSqlFormat(fieldName, isAnd);
		} else {
			// 用于生成主键，update和delete必须有条件
			return (isAnd ? " and " : "") + StringUtils.allToUpper(fieldName) + " = #{" + fieldName + "}"
					+ (isAnd ? "" : ",") + "\n";
		}
	}

	private static String dynamicUpdateBatchSqlFormat(String fieldName,
												 boolean isAnd,	boolean isIf) {
		if (isIf) {
			return dynamicSqlFormat(fieldName, isAnd);
		} else {
			// 用于生成主键，update和delete必须有条件
			return StringUtils.allToUpper(fieldName) + " = #{i." + fieldName + "}"+ "\n";
		}
	}

	private static String dynamicSqlFormat(String fieldName, boolean isAnd) {
		return "<if test=\"" + fieldName + " !=null\"> \n" + (isAnd ? " and " : "") + StringUtils.allToUpper(fieldName)
				+ " = #{" + fieldName + "}" + (isAnd ? "" : ",") + "\n" + "</if>";
	}

	// 生成XML文件
	public static void buildXML(String docPath, Class<?> clazz,
								String tableName,String namespaceName,
								String xmlName) throws IOException {
		BufferedWriter bufferedWriter = null;
		try {
			if (!clazz.getSimpleName().contains("Domain") && !clazz.getSimpleName().contains("DAO")) {
				return;
			}

			String namespace = namespaceName+"."+xmlName;

			String fileName = xmlName;// xxxDAO

			// xml头部

			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
			sb.append("\n");
			sb.append(
					"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
			sb.append("\n");
			sb.append("<mapper namespace=\"" + namespace + "\">");
			sb.append("\n");

			sb.append(buildFindByKey(clazz, tableName));

			sb.append(buildFindByWhere(clazz, tableName));

			sb.append(buildInsert(clazz, tableName));
			sb.append(buildUpdate(clazz, tableName));
			sb.append(buildDelete(clazz, tableName));


			sb.append(buildInsertBatch(clazz, tableName));
			sb.append(buildUpdateBatch(clazz, tableName));
			sb.append(buildDeleteBatch(clazz, tableName));

			sb.append("</mapper>");

			if (!FileUtil.isDirectory(new File(docPath))) {
				throw new FileNotFoundException(docPath + "不存在,请确认路径.");
			}

			File file = FileUtil.createFile(docPath, fileName + ".xml");

			bufferedWriter = new BufferedWriter(new FileWriter(file));

			bufferedWriter.write(sb.toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != bufferedWriter) {
				bufferedWriter.flush();
				bufferedWriter.close();
			}
		}
	}

	// 生成mapper文件
	public static void buildMapper(String docPath,
								   String packagePath,String domainNamePackage,
								String mapperName,String domainName) throws IOException {
		BufferedWriter bufferedWriter = null;
		try {


			StringBuffer sb = new StringBuffer();
			sb.append("package "+packagePath+";");
			sb.append("\n");
			sb.append("import org.apache.ibatis.annotations.Mapper;");
			sb.append("\n");
			sb.append("import "+domainNamePackage+"."+domainName+";");
			sb.append("\n");
			sb.append("import org.springframework.stereotype.Repository;");
			sb.append("\n");
			sb.append("import java.util.List;");
			sb.append("\n");
			sb.append("/**");
			sb.append("\n");
			sb.append("* @date ：Created By "+new Date());
			sb.append("\n");
			sb.append("* @description："+mapperName);
			sb.append("\n");
			sb.append(" */");
			sb.append("\n");
			sb.append("@Repository");
			sb.append("\n");
			sb.append("@Mapper");
			sb.append("\n");
			sb.append("public interface "+mapperName+" extends BaseMapper<"+domainName+"> {");
			sb.append("\n");
			sb.append("}");


			File file = FileUtil.createFile(docPath, mapperName + ".java");

			bufferedWriter = new BufferedWriter(new FileWriter(file));

			bufferedWriter.write(sb.toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != bufferedWriter) {
				bufferedWriter.flush();
				bufferedWriter.close();
			}
		}
	}


	// 生成XML文件
	public static void buildService(String docPath, String packagePath,
									String domainNamePackage,
								   String serviceName,String domainName) throws IOException {
		BufferedWriter bufferedWriter = null;
		try {


			StringBuffer sb = new StringBuffer();
			sb.append("package "+packagePath+";");
			sb.append("\n");
			sb.append("import  "+domainNamePackage+"."+domainName+";");
			sb.append("\n");
			sb.append("import java.util.List;");
			sb.append("\n");
			sb.append("/**");
			sb.append("\n");
			sb.append("* @date ：Created By "+new Date());
			sb.append("\n");
			sb.append("* @description："+serviceName);
			sb.append("\n");
			sb.append(" */");
			sb.append("\n");
			sb.append("public interface "+serviceName+" extends BaseService<"+domainName+"> {");
			sb.append("\n");
			sb.append("}");


			File file = FileUtil.createFile(docPath, serviceName + ".java");

			bufferedWriter = new BufferedWriter(new FileWriter(file));

			bufferedWriter.write(sb.toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != bufferedWriter) {
				bufferedWriter.flush();
				bufferedWriter.close();
			}
		}
	}

	// 生成serviceImpl文件
	public static void buildServiceImpl(String docPath,
										String packagePath,
										String domainNamePackage,
										String domainName,
										String mapperPackage,
										String mapperName,
										String servicePackage,
										String serviceName,
									String serviceImplName
										) throws IOException {
		BufferedWriter bufferedWriter = null;
		try {


			StringBuffer sb = new StringBuffer();
			sb.append("package "+packagePath+";");
			sb.append("\n");
			sb.append("import "+domainNamePackage+"."+domainName+";");
			sb.append("\n");
			sb.append("import "+mapperPackage+"."+mapperName+";");
			sb.append("\n");
			sb.append("import "+servicePackage+"."+serviceName+";");
			sb.append("\n");
			sb.append("import org.springframework.beans.factory.annotation.Autowired;");
			sb.append("\n");
			sb.append("import org.springframework.stereotype.Service;");
			sb.append("\n");
			sb.append("\n");
			sb.append("\n");
			sb.append("import java.util.List;");
			sb.append("\n");
			sb.append("/**");
			sb.append("\n");
			sb.append("* @date ：Created By "+new Date());
			sb.append("\n");
			sb.append("* @description："+serviceImplName);
			sb.append("\n");
			sb.append(" */");
			sb.append("\n");
			sb.append("@Service");
			sb.append("\n");

			sb.append("public class "+serviceImplName+" implements "+ serviceName +" {");
			sb.append("\n");
			sb.append("    @Autowired");
			sb.append("\n");
			int mapperIndex = mapperPackage.lastIndexOf(".");
			sb.append("    private "+mapperName+" " +Character.toLowerCase(mapperName.charAt(0))).append(mapperName.substring(1)+";");
			sb.append("\n");
			sb.append("\n");
			sb.append("    @Override");
			sb.append("\n");
			sb.append("    public int insert("+domainName+" var1) {\n" +
					"        return "+Character.toLowerCase(mapperName.charAt(0))).append(mapperName.substring(1)+".insert(var1);\n" +
					"    }");
			sb.append("\n");
			sb.append("    @Override");
			sb.append("\n");
			sb.append("    public int delete("+domainName+" var1) {\n" +
					"        return "+Character.toLowerCase(mapperName.charAt(0))).append(mapperName.substring(1)+".delete(var1);\n" +
					"\n" +
					"    }");
			sb.append("\n");
			sb.append("    @Override");
			sb.append("\n");
			sb.append("    public int update("+domainName+" var1) {\n" +
					"        return "+Character.toLowerCase(mapperName.charAt(0))).append(mapperName.substring(1)+".update(var1);\n" +
					"\n" +
					"    }");
			sb.append("\n");
			sb.append("\n");
			sb.append("    @Override");
			sb.append("\n");
			sb.append("    public "+domainName+" findByKey("+domainName+" var1) {\n" +
					"        return "+Character.toLowerCase(mapperName.charAt(0))).append(mapperName.substring(1)+".findByKey(var1);\n" +
					"\n" +
					"    }");
			sb.append("\n");

			sb.append("    @Override");
			sb.append("\n");
			sb.append("    public List<"+domainName+"> findByWhere("+domainName+" var1) {\n" +
					"        return "+Character.toLowerCase(mapperName.charAt(0))).append(mapperName.substring(1)+".findByWhere(var1);\n" +
					"\n" +
					"    }");
			sb.append("\n");

			sb.append("    @Override");
			sb.append("\n");
			sb.append("    public int insertBatch(List<"+domainName+"> vars) " +
					"{\n" +
					"        return "+Character.toLowerCase(mapperName.charAt(0))).append(mapperName.substring(1)+".insertBatch(vars);\n" +
					"\n" +
					"    }");
			sb.append("\n");

			sb.append("    @Override");
			sb.append("\n");
			sb.append("    public int updateBatch(List<"+domainName+"> vars) {\n" +
					"        return "+Character.toLowerCase(mapperName.charAt(0))).append(mapperName.substring(1)+".updateBatch(vars);\n" +
					"\n" +
					"    }");
			sb.append("\n");
			sb.append("}");


			File file = FileUtil.createFile(docPath, serviceImplName + ".java");

			bufferedWriter = new BufferedWriter(new FileWriter(file));

			bufferedWriter.write(sb.toString());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != bufferedWriter) {
				bufferedWriter.flush();
				bufferedWriter.close();
			}
		}
	}

	// findByKey
	private static String buildFindByKey(Class<?> clazz, String tableName) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("<select id=\"findByKey\" parameterType=\"").append(clazz.getSimpleName()).append("\" resultType=\"")
				.append(clazz.getSimpleName()).append("\" useCache=\"false\">");
		sb.append("\n");
		sb.append("select ").append(getColumns(clazz)).append(" from ").append(tableName);
		List<String> primaryKeys = getPrimaryKeys(clazz);
		if (0 != primaryKeys.size()) {
			sb.append("<where>");
			sb.append("\n");
			for (String fieldName : getPrimaryKeys(clazz)) {
				sb.append(dynamicSqlFormat(fieldName, true, false));
				sb.append("\n");
			}
			sb.append("</where>");
		}
		sb.append("\n");
		sb.append("</select>");
		sb.append("\n");
		return sb.toString();
	}

	// findByWhere
	private static String buildFindByWhere(Class<?> clazz, String tableName) {
		StringBuffer sb = new StringBuffer();
		sb.append("<select id=\"findByWhere\" parameterType=\"").append(clazz.getSimpleName())
				.append("\" resultType=\"").append(clazz.getSimpleName()).append("\" useCache=\"false\">");
		sb.append("\n");
		sb.append("select ").append(getColumns(clazz)).append(" from ").append(tableName);
		sb.append("\n");
		sb.append("<where>");
		sb.append("\n");
		Field[] fields = ReflectUtil.getFields(clazz);
		for (Field field : fields) {
			sb.append(dynamicSqlFormat(field.getName(), true));
			sb.append("\n");
		}
		sb.append("</where>");
		sb.append("\n");
		sb.append("</select>");
		sb.append("\n");
		return sb.toString();
	}

	private static String buildInsert(Class<?> clazz, String tableName) {
		StringBuffer sb = new StringBuffer();
		sb.append("<insert id=\"insert\" parameterType=\"" + clazz.getSimpleName() + "\" flushCache=\"true\">");
		sb.append("\n");
		sb.append("insert into ");
		sb.append(tableName);
		sb.append("(");
		sb.append(getColumns(clazz));
		sb.append(") values ");
		sb.append(getInsert(clazz));
		sb.append("\n");
		sb.append("</insert>");
		sb.append("\n");
		return sb.toString();
	}

	private static String buildUpdate(Class<?> clazz, String tableName) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<update id=\"update\" parameterType=\"" + clazz.getSimpleName() + "\" flushCache=\"true\">");
		sb.append("\n");
		sb.append("update ");
		sb.append(tableName);
		sb.append("\n");
		sb.append("<set>");
		sb.append("\n");
		sb.append(getUpdate(clazz));
		sb.append("</set>");
		sb.append("\n");
		List<String> primaryKeys = getPrimaryKeys(clazz);
		if (0 != primaryKeys.size()) {
			sb.append("<where>");
			sb.append("\n");
			for (String fieldName : getPrimaryKeys(clazz)) {
				sb.append(dynamicSqlFormat(fieldName, true, false));
				sb.append("\n");
			}
			sb.append("</where>");
		} else {
			throw new Exception("生成update语句无主键,存在隐患....");
		}
		sb.append("\n");
		sb.append("</update>");
		sb.append("\n");
		return sb.toString();
	}



	private static String buildInsertBatch(Class<?> clazz, String tableName) {
		StringBuffer sb = new StringBuffer();
		sb.append("<insert id=\"insertBatch\" flushCache=\"true\">");
		sb.append("\n");
		sb.append("insert into ");
		sb.append(tableName);
		sb.append("(");
		sb.append(getColumns(clazz));
		sb.append(") values ");
		sb.append("\n");
		sb.append("<foreach collection=\"list\" separator=\",\" " +
				"item=\"item\">");
		sb.append(getInsertBatch(clazz));
		sb.append("\n");
		sb.append("</foreach>");
		sb.append("\n");
		sb.append("</insert>");
		sb.append("\n");
		return sb.toString();
	}


	private static String buildUpdateBatch(Class<?> clazz, String tableName) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<update id=\"updateBatch\" parameterType=\"list\">");
		sb.append("\n");
		sb.append("update ");
		sb.append(tableName);
		sb.append("\n");
		sb.append("<trim prefix=\"set\" suffixOverrides=\",\">");
		sb.append("\n");
		sb.append(getUpdateBatch(clazz));
		sb.append("</trim>");
		sb.append("\n");
		List<String> primaryKeys = getPrimaryKeys(clazz);
		if (0 != primaryKeys.size()) {
			sb.append("<where>");
			sb.append("\n");
			sb.append("<foreach collection=\"list\" separator=\"or\" item=\"i\" index=\"index\" >");
			sb.append("\n");
			sb.append("(");
			for(int i=0;i<getPrimaryKeys(clazz).size();i++){
				if(i==0){
					sb.append(dynamicUpdateBatchSqlFormat(getPrimaryKeys(clazz).get(i), false,
							false));
				}else{
					sb.append(" and ");
					sb.append(dynamicUpdateBatchSqlFormat(getPrimaryKeys(clazz).get(i), true,
							false));
				}
			}
			sb.append(")");
			sb.append("</foreach>");
			sb.append("</where>");
		} else {
			throw new Exception("生成update语句无主键,存在隐患....");
		}
		sb.append("\n");
		sb.append("</update>");
		sb.append("\n");
		return sb.toString();
	}

	private static String buildDeleteBatch(Class<?> clazz, String tableName) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<delete id=\"deleteBatch\" parameterType=\"list\">");
		sb.append("\n");
		sb.append("delete from ");
		sb.append(tableName);
		sb.append("\n");
		List<String> primaryKeys = getPrimaryKeys(clazz);
		if (0 != primaryKeys.size()) {
			sb.append("<where>");
			sb.append("\n");
			sb.append("(");
			sb.append(primaryKeys.stream().map(t->StringUtils.allToUpper(t)).collect(Collectors.joining(
					",")));
			sb.append(") in ");
			sb.append("<foreach collection=\"list\" separator=\",\" item=\"i\" index=\"index\"  open=\"(\" close=\")\">");
			sb.append("\n");
			sb.append("(");
			for(int i=0;i<getPrimaryKeys(clazz).size();i++){
				sb.append("#{i." + getPrimaryKeys(clazz).get(i) + "}");
				if(i!=(getPrimaryKeys(clazz).size()-1)){
					sb.append(",");
				}
			}
			sb.append(")");
			sb.append("</foreach>");
			sb.append("</where>");
		} else {
			throw new Exception("生成update语句无主键,存在隐患....");
		}
		sb.append("\n");
		sb.append("</delete>");
		sb.append("\n");
		return sb.toString();
	}

	private static String buildDelete(Class<?> clazz, String tableName) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("<delete id=\"delete\" parameterType=\"" + clazz.getSimpleName() + "\" flushCache=\"true\">");
		sb.append("\n");
		sb.append("delete from ");
		sb.append(tableName);
		sb.append("\n");

		List<String> primaryKeys = getPrimaryKeys(clazz);
		if (0 != primaryKeys.size()) {
			sb.append("<where>");
			sb.append("\n");
			for (String fieldName : getPrimaryKeys(clazz)) {
				sb.append(dynamicSqlFormat(fieldName, true, false));
				sb.append("\n");
			}
			sb.append("</where>");
		} else {
			throw new Exception("生成update语句无主键,存在隐患....");
		}
		sb.append("\n");
		sb.append("</delete>");
		sb.append("\n");
		return sb.toString();
	}

}
