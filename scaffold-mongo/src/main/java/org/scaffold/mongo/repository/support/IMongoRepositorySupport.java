/**
 * Author : chiziyue
 * Date : 2022年5月15日 下午9:40:47
 * Title : org.scaffold.mongo.service.support.MongoRepositorySupport.java
 *
**/
package org.scaffold.mongo.repository.support;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.scaffold.common.annotation.AnnotationUtils;
import org.scaffold.common.annotation.MongoWriteIgnore;
import org.scaffold.common.json.GsonUtils;
import org.scaffold.common.reflect.ReflectUtil;
import org.scaffold.logger.log.ScaffoldLogger;
import org.scaffold.mongo.service.page.Page;

import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.UpdateManyModel;
import com.mongodb.client.model.UpdateOneModel;
import com.mongodb.client.model.UpdateOptions;
import com.mongodb.client.model.WriteModel;
import com.mongodb.client.result.DeleteResult;

public interface IMongoRepositorySupport {

	static final ScaffoldLogger SCAFFOLD_LOGGER = new ScaffoldLogger("MongoRepository ");
	static final String separatorChar = "#";

	MongoCollection<Document> getMongoCollection(String collectionName);

	public String getCollectionName(String mon);

	default String generateCollectionName(String... params) {
		return String.join(separatorChar, params);
	}

	default String generateCollectionName(String mon, Class<?> clazz) {
		return String.join(separatorChar, mon, (String) AnnotationUtils.getAnnotationValue(clazz,
				org.springframework.data.mongodb.core.mapping.Document.class, "collection"));
	}
	
	default BulkWriteResult bulkWrite(List<WriteModel<Document>> entitys, String mon) {
		return getMongoCollection(getCollectionName(mon)).bulkWrite(entitys);
	}

	default List<WriteModel<Document>> insertMany(List<Document> documents) {
		List<WriteModel<Document>> requests = new ArrayList<>();
		documents.stream().forEach(d -> {
			InsertOneModel<Document> insertOneModel = new InsertOneModel<Document>(d);
			requests.add(insertOneModel);
		});
		return requests;
	}

	default List<WriteModel<Document>> deleteMany(List<Document> documents, MongoDeleteFilter2 mongoDeleteFilter2) {
		List<WriteModel<Document>> requests = new LinkedList<>();
		documents.stream().forEach(d -> {
			DeleteOneModel<Document> deleteOneModel = new DeleteOneModel<>(mongoDeleteFilter2.filter(d));
			requests.add(deleteOneModel);
		});
		return requests;
	}

	default <V> List<WriteModel<Document>> updateMany2(List<V> params, MongoUpdateFilter2<V> mongoUpdateFilter,
			boolean isUpsert) {
		List<WriteModel<Document>> requests = new ArrayList<>();
		params.stream().forEach(p -> {
			Bson filter = mongoUpdateFilter.filter(p);
			Bson update = mongoUpdateFilter.update(mongoUpdateFilter.setUpdate(p));
//			UpdateOneModel<Document> updateOneModel = new UpdateOneModel<>(filter, update,
//					new UpdateOptions().upsert(isUpsert));

			UpdateManyModel<Document> updateManyModel = new UpdateManyModel<>(filter, update,
					new UpdateOptions().upsert(isUpsert));

			SCAFFOLD_LOGGER.debug("filter : " + filter + "   update:" + update);

			requests.add(updateManyModel);
		});

		return requests;
	}

	default List<WriteModel<Document>> updateMany(List<Document> documents, MongoUpdateFilter mongoUpdateFilter,
			boolean isUpsert) {
		List<WriteModel<Document>> requests = new ArrayList<>();
		documents.stream().forEach(d -> {
			Bson filter = mongoUpdateFilter.filter(d);
			Bson update = mongoUpdateFilter.update(d);
			UpdateOneModel<Document> updateOneModel = new UpdateOneModel<>(filter, update,
					new UpdateOptions().upsert(isUpsert));

			requests.add(updateOneModel);
		});
		return requests;
	}

	default List<WriteModel<Document>> updateMany(List<Document> documents, MongoUpdateFilter mongoUpdateFilter,
			boolean isUpsert, List<? extends Bson> arrayFilters) {
		List<WriteModel<Document>> requests = new ArrayList<>();
		documents.stream().forEach(d -> {
			Bson filter = mongoUpdateFilter.filter(d);
			Bson update = mongoUpdateFilter.update(d);
			UpdateOneModel<Document> updateOneModel = new UpdateOneModel<>(filter, update,
					new UpdateOptions().upsert(isUpsert).arrayFilters(arrayFilters));

			requests.add(updateOneModel);
		});
		return requests;
	}

	default List<Document> toDocuments(List<?> list) {
		List<Document> documents = Collections.synchronizedList(new ArrayList<>());
		list.parallelStream().forEach(m -> {
//			documents.add(Document.parse(GsonUtils.getIgnorReadGson().toJson(m)));
			documents.add(Document.parse(GsonUtils.get().toJson(m)));
		});
		return documents;
	}

	default <T> List<Document> toDocuments(List<T> list, ToDocumentCallBack<T> callBack) {
		List<Document> documents = Collections.synchronizedList(new ArrayList<>());
		list.parallelStream().forEach(m -> {
//			documents.add(Document.parse(GsonUtils.toJsonIgnoreNull(callBack.call(m))));
			documents.add(Document.parse(GsonUtils.get().toJson(callBack.call(m))));
		});
		return documents;
	}

	default <T> List<Document> toDocuments(Collection<T> list, ToDocumentCallBack<T> callBack) {
		List<Document> documents = Collections.synchronizedList(new ArrayList<>());
		list.parallelStream().forEach(m -> {
//			documents.add(Document.parse(JSONUtil.toJsonString(callBack.call(m))));
			documents.add(Document.parse(GsonUtils.get().toJson(callBack.call(m))));
		});
		return documents;
	}

	interface MongoFindFilter {
		Bson filter();

		default Bson getSort() {
			return new Document("id", 1);
		}

		default Page getPage() {
			return null;
		}

		/**
		 * 升序
		 * 
		 * @return
		 */
		default int up() {
			return 1;
		}

		/**
		 * 降序
		 * 
		 * @return
		 */
		default int down() {
			return -1;
		}
	}

	interface MongoUpdateFilter2<T> {
		Bson filter(T t);

		Document setUpdate(T t);

		default Bson update(Document param) {
			return new Document("$set", param);
		}

	}

	interface MongoUpdateFilter {
		Bson filter(Document param);

		default Bson update(Document param) {
			return new Document("$set", param);
		}

	}

	interface MongoDeleteFilter {
		Bson filter();
	}

	interface MongoDeleteFilter2 {

		Bson filter(Document param);
	}

	interface MongoAggregateFilter {

		List<? extends Bson> getPipeline();

		String info();

		Bson setMatch();

		default Bson getMatch() {
			return new Document("$match", setMatch());
		}

	}

	interface MongoAggregateGroupFilter extends MongoAggregateFilter {
		default List<? extends Bson> getPipeline() {
			List<Bson> pipeLine = new LinkedList<>();
			pipeLine.add(getMatch());
			pipeLine.add(getGroup());
			return pipeLine;
		};

		Bson setGroup();

		default Bson getGroup() {
			return new Document("$group", setGroup());
		}

		@Override
		default String info() {
			return getMatch().toString() + "+" + getGroup();
		}
	}

	interface MongoAggregateGraphLookupFilter extends MongoAggregateFilter {
		Bson setGraphLookup();

		default Bson getGraphLookup() {
			return new Document("$graphLookup", setGraphLookup());
		}

		default List<? extends Bson> getPipeline() {
			List<Bson> pipeLine = new LinkedList<>();
			pipeLine.add(getMatch());
			pipeLine.add(getGraphLookup());
			return pipeLine;
		}

		@Override
		default String info() {
			return getMatch().toString() + "+" + setGraphLookup();
		}
	}

	interface MongoAggregateLookupFilter extends MongoAggregateFilter {

		Bson setLookup();

		default Bson getLookup() {
			return new Document("$lookup", setLookup());
		}

		@Override
		default List<? extends Bson> getPipeline() {
			List<Bson> pipeLine = new LinkedList<>();
			pipeLine.add(getLookup());
			pipeLine.add(getMatch());
			return pipeLine;
		}

		@Override
		default String info() {
//			return GsonUtils.toJson(getPipeline());
			return GsonUtils.get().toJson(getPipeline());
		}

	}

	default void insertMany(String collectionName, List<Document> documents) {
		getMongoCollection(collectionName).insertMany(documents);
	}

	default List<String> findMany(String collectionName, MongoFindFilter filter) {
		FindIterable<String> findIterable = getMongoCollection(collectionName).find(filter.filter(), String.class);
		MongoCursor<String> mongoCursor = findIterable.iterator();
		List<String> result = new ArrayList<>();
		while (mongoCursor.hasNext()) {
			result.add(mongoCursor.next());
		}
		return result;
	}

	default <T> List<T> findManySort(String collectionName, MongoFindFilter filter, Class<T> clazz) {
		MongoCollection<Document> collection = getMongoCollection(collectionName);
		FindIterable<Document> findIterable = collection.find(filter.filter()).sort(filter.getSort());
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		List<T> result = new ArrayList<>();
		while (mongoCursor.hasNext()) {
			Document document = mongoCursor.next();
			result.add(GsonUtils.get().readValue(
					document.toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build()), clazz));
		}

		SCAFFOLD_LOGGER.debug(collection.getNamespace().getFullName() + "::findMany::" + filter.filter().toString()
				+ "====" + result.size());

		return result;
	}

	default <T> List<T> findManyByPage(String collectionName, MongoFindFilter filter, Class<T> clazz) {
		MongoCollection<Document> collection = getMongoCollection(collectionName);
		FindIterable<Document> findIterable = null;
		if (filter.getPage() != null && filter.getPage().getPageSize() != -1) {
			findIterable = collection.find(filter.filter()).sort(filter.getSort())
					.skip(Math.multiplyExact(filter.getPage().getPageSize(), filter.getPage().getPageCurrent() - 1))
					.limit(filter.getPage().getPageSize());
		} else {
			findIterable = collection.find(filter.filter()).sort(filter.getSort());
		}
		long count = collection.countDocuments(filter.filter());
		filter.getPage().setTotalRow((int) count);
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		List<T> result = new ArrayList<>();
		while (mongoCursor.hasNext()) {
			Document document = mongoCursor.next();
			result.add(GsonUtils.get().readValue(
					document.toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build()), clazz));
		}

		SCAFFOLD_LOGGER.debug(collection.getNamespace().getFullName() + "::findManyByPage::"
				+ filter.filter().toString() + "====" + result.size());
		return result;
	}

	default <T> List<T> findMany(String collectionName, MongoFindFilter filter, Class<T> clazz) {
		MongoCollection<Document> collection = getMongoCollection(collectionName);
		FindIterable<Document> findIterable = collection.find(filter.filter());
		MongoCursor<Document> mongoCursor = findIterable.iterator();
		List<T> result = new ArrayList<>();
		List<String> removeList = new ArrayList<>();
		Field[] fields = ReflectUtil.getFields(clazz);
		for (Field field : fields) {
			if (field.isAnnotationPresent(MongoWriteIgnore.class)) {
				removeList.add(field.getName());
			}
		}
		while (mongoCursor.hasNext()) {
			Document document = mongoCursor.next();
			removeList.forEach(name -> {
				document.remove(name);
			});
			String json = document.toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build());
			result.add(GsonUtils.get().readValue(json, clazz));
		}

		SCAFFOLD_LOGGER.info(collection.getNamespace().getFullName() + "::findMany::" + filter.filter().toString()
				+ "====" + result.size());
		return result;
	}

	default long getCount(String collectionName, MongoFindFilter filter) {
		MongoCollection<Document> collection = getMongoCollection(collectionName);
		return collection.countDocuments(filter.filter());
	}

	default long deleteMany(String collectionName, MongoDeleteFilter filter) {
		MongoCollection<Document> collection = getMongoCollection(collectionName);
		DeleteResult deleteResult = collection.deleteMany(filter.filter());
		SCAFFOLD_LOGGER.info(collection.getNamespace().getFullName() + "::deleteMany::" + filter.filter().toString()
				+ "====" + deleteResult.getDeletedCount());

		return deleteResult.getDeletedCount();
	}

	default List<Document> aggregate(String collectionName, MongoAggregateFilter filter) {
		MongoCollection<Document> collection = getMongoCollection(collectionName);
		List<? extends Bson> pipeline = filter.getPipeline();

		AggregateIterable<Document> aggregateIterable = collection.aggregate(pipeline);

		MongoCursor<Document> mongoCursor = aggregateIterable.iterator();

		List<Document> result = new ArrayList<>();
		while (mongoCursor.hasNext()) {
			Document document = mongoCursor.next();
			result.add(document);
		}

		SCAFFOLD_LOGGER
				.info(collection.getNamespace().getFullName() + ":aggregate:" + filter.info() + "====" + result.size());

		return result;
	}

	interface ToDocumentCallBack<T> {
		T call(T t);
	}

}
