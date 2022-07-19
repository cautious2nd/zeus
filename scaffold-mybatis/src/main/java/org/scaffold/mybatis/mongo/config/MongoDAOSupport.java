/**
 * Author : czy
 * Date : 2019年4月18日 下午7:37:21
 * Title : com.riozenc.titanTool.mongo.dao.MongoDAO.java
 **/
package org.scaffold.mybatis.mongo.config;

import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.scaffold.common.json.GsonUtils;
import org.scaffold.mybatis.generator.MongoReflectionIgnore;
import org.scaffold.mybatis.generator.util.reflect.ReflectUtil;
import org.scaffold.mybatis.pageHelper.PageEntity;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public interface MongoDAOSupport {
    static final Log logger = LogFactory.getLog("mongodb ");
    static final String separatorChar = "#";


    default String getCollectionName(String date, String name) {
        return date + separatorChar + name;
    }

    default List<Document> toDocuments(List<?> list) {
        List<Document> documents = new LinkedList<>();
        list.stream().forEach(m -> {
            documents.add(Document.parse(GsonUtils.get().toJson(m)));
        });
        return documents;
    }

    default <T> Document toDocument(T t, ToDocumentCallBack<T> callBack) {
        return Document.parse(GsonUtils.get().toJsonIgnoreNull(callBack.call(t)));
    }

    default <T> List<Document> toDocuments(List<T> list, ToDocumentCallBack<T> callBack) {
        List<Document> documents = new LinkedList<>();
        list.stream().forEach(m -> {
//			documents.add(Document.parse(JSONUtil.toJsonString(callBack.call(m))));
            documents.add(Document.parse(GsonUtils.get().toJsonIgnoreNull(callBack.call(m))));
        });
        return documents;
    }

    default <T> List<Document> toDocuments(Collection<T> list, ToDocumentCallBack<T> callBack) {
        List<Document> documents = new LinkedList<>();
        list.stream().forEach(m -> {
            documents.add(Document.parse(GsonUtils.get().toJsonIgnoreNull(callBack.call(m))));
        });
        return documents;
    }

    default MongoCollection<Document> getCollection(MongoTemplate mongoTemplate, String date, String name) {
        return mongoTemplate.getCollection(getCollectionName(date, name));
    }

    default void insertMany(MongoCollection<Document> collection, List<Document> documents) {
        collection.insertMany(documents);
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

            logger.debug("filter : " + filter + "   update:" + update);

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

    default List<WriteModel<Document>> updateManyByOne(Document document,
                                                       MongoUpdateFilter mongoUpdateFilter,
                                                       boolean isUpsert) {
        List<WriteModel<Document>> requests = new ArrayList<>();
        Bson filter = mongoUpdateFilter.filter(document);
        Bson update = mongoUpdateFilter.update(document);
        UpdateManyModel<Document> updateManyModel = new UpdateManyModel<>(filter, update,
                new UpdateOptions().upsert(isUpsert));

        requests.add(updateManyModel);
        return requests;
    }

    default List<WriteModel<Document>> unsetManyByOne(Document document,
                                                      MongoUpdateFilter mongoUpdateFilter,
                                                      boolean isUpsert) {
        List<WriteModel<Document>> requests = new ArrayList<>();
        Bson filter = mongoUpdateFilter.filter(document);
        Bson update = mongoUpdateFilter.unUpdate(document);
        UpdateManyModel<Document> updateManyModel = new UpdateManyModel<>(filter, update,
                new UpdateOptions().upsert(isUpsert));

        requests.add(updateManyModel);
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

    default List<String> findMany(MongoCollection<?> collection, MongoFindFilter filter) {
        FindIterable<String> findIterable = collection.find(filter.filter(), String.class);
        MongoCursor<String> mongoCursor = findIterable.iterator();
        List<String> result = new ArrayList<>();
        while (mongoCursor.hasNext()) {
            result.add(mongoCursor.next());
        }
        return result;
    }

    // TODO 后期扩展：多库，多次查询后汇总结果
    default <T> List<T> findMany(MongoTemplate mongoTemplate,
                                 String collectionName, MongoFindFilter filter, Class<T> clazz) {
        return findMany(mongoTemplate.getCollection(collectionName), filter, clazz);
    }

    default <T> List<T> findManySort(MongoTemplate mongoTemplate,
                                     String collectionName, MongoFindFilter filter, Class<T> clazz) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        FindIterable<Document> findIterable = collection.find(filter.filter()).sort(filter.getSort());
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        List<T> result = new ArrayList<>();
        while (mongoCursor.hasNext()) {
            Document document = mongoCursor.next();
            result.add(GsonUtils.get().readValue(
                    document.toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build()), clazz));
        }

        logger.debug(collection.getNamespace().getFullName() + "::findMany::" + filter.filter().toString() + "===="
                + result.size());

        return result;
    }

    default <T> List<T> findManyByPage(MongoTemplate mongoTemplate,
                                       String collectionName, MongoFindFilter filter, Class<T> clazz) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        FindIterable<Document> findIterable = null;
        if (filter.getPageEntity() != null && filter.getPageEntity().getPageSize() != -1 && filter.getPageEntity().getPageSize() != 0) {
            int pageNum=filter.getPageEntity().getPageNum();
            if(pageNum<=0){
                pageNum=filter.getPageEntity().getPageNo();
            }
            if(pageNum<=0){
                pageNum=filter.getPageEntity().getPageCurrent();
            }

            findIterable = collection.find(filter.filter()).sort(filter.getSort())
                    .skip(Math.multiplyExact(filter.getPageEntity().getPageSize(),
                            pageNum - 1))
                    .limit(filter.getPageEntity().getPageSize());
        } else {
            findIterable = collection.find(filter.filter()).sort(filter.getSort());
        }
        long count = collection.countDocuments(filter.filter());
        filter.getPageEntity().setTotalRow((int) count);
        filter.getPageEntity().setTotal((int) count);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        List<T> result = new ArrayList<>();
        while (mongoCursor.hasNext()) {
            Document document = mongoCursor.next();
            result.add(GsonUtils.get().readValue(
                    document.toJson(JsonWriterSettings.builder().outputMode(JsonMode.RELAXED).build()), clazz));
        }

        logger.debug(collection.getNamespace().getFullName() + "::findManyByPage::" + filter.filter().toString()
                + "====" + result.size());
        return result;
    }

    default <T> List<T> findMany(MongoCollection<Document> collection, MongoFindFilter filter, Class<T> clazz) {
        FindIterable<Document> findIterable = collection.find(filter.filter());
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        List<T> result = new ArrayList<>();
        List<String> removeList = new ArrayList<>();
        Field[] fields = ReflectUtil.getFields(clazz);
        for (Field field : fields) {
            if (field.isAnnotationPresent(MongoReflectionIgnore.class)) {
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

        logger.info(collection.getNamespace().getFullName() + "::findMany::" + filter.filter().toString() + "===="
                + result.size());
        return result;
    }

    default long getCount(MongoTemplate mongoTemplate, String collectionName,
                          MongoFindFilter filter) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        return collection.countDocuments(filter.filter());
    }

    default <T> List<T> getDistinct(MongoTemplate mongoTemplate,
                                    String collectionName, String fieldName,
                                    MongoFindFilter filter, Class<T> clazz) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);
        DistinctIterable<T> distinctIterable = collection.distinct(fieldName,
                filter.filter(), clazz);
        MongoCursor<T> mongoCursor = distinctIterable.iterator();
        List<T> result = new ArrayList<>();
        while (mongoCursor.hasNext()) {
            T document = mongoCursor.next();
            result.add(document);
        }

        logger.info(collection.getNamespace().getFullName() + "::getDistinct::" + filter.filter().toString() + "===="
                + result.size());
        return result;
    }

    default long deleteMany(MongoTemplate mongoTemplate, String collectionName, MongoDeleteFilter filter) {

        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);

        DeleteResult deleteResult = collection.deleteMany(filter.filter());
        logger.info(collection.getNamespace().getFullName() + "::deleteMany::" + filter.filter().toString() + "===="
                + deleteResult.getDeletedCount());

        return deleteResult.getDeletedCount();
    }

    default List<Document> aggregate(MongoTemplate mongoTemplate, String collectionName, MongoAggregateFilter filter) {
        MongoCollection<Document> collection = mongoTemplate.getCollection(collectionName);

        List<? extends Bson> pipeline = filter.getPipeline();

        AggregateIterable<Document> aggregateIterable = collection.aggregate(pipeline);


        MongoCursor<Document> mongoCursor = aggregateIterable.iterator();

        List<Document> result = new ArrayList<>();
        while (mongoCursor.hasNext()) {
            Document document = mongoCursor.next();
            result.add(document);
        }

        logger.info(collection.getNamespace().getFullName() + ":aggregate:" + filter.info() + "====" + result.size());

        return result;
    }

    interface MongoFindFilter {
        Bson filter();

        default Bson getSort() {
            return new Document("id", 1);
        }

        default PageEntity getPageEntity() {
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

        default Bson unUpdate(Document param) {
            return new Document("$unset", param);
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
        @Override
        default List<? extends Bson> getPipeline() {
            List<Bson> pipeLine = new LinkedList<>();
            pipeLine.add(getMatch());
            pipeLine.add(getGroup());
            return pipeLine;
        }

        ;

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

        @Override
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
            return GsonUtils.get().toJson(getPipeline());
        }

    }

    interface ToDocumentCallBack<T> {
        T call(T t);
    }
}
