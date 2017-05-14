package city.commerce.repository.mapper;

import city.commerce.entity.DivisionEntity;
import city.commerce.entity.ProductAttributeValueEntity;
import city.commerce.entity.ProductEntity;
import city.commerce.entity.ProductSkuEntity;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author young.alway@gmail.com
 * @since 2017-04-16
 */
public class Mappers {

    public String product_insert() {
        return insert(ProductEntity.class,
                "name", "code", "description", "storeId", "vendorId", "categoryId", "price", "currency", "inventory",
                "created", "modified", "status"
        );
    }

    public String product_update() {
        return update(ProductEntity.class, "id",
                "name", "code", "description", "storeId", "vendorId", "categoryId", "price", "currency", "inventory", "modified", "status"
        );
    }

    public String product_attribute_value_insert() {
        return insert(
                ProductAttributeValueEntity.class,
                "productId", "skuid", "attributeId", "attributeValueId", "value"
        );
    }

    public String product_attribute_value_update() {
        return insert(
                ProductAttributeValueEntity.class, "id",
                "productId", "skuId", "attributeId", "attributeValueId", "value"
        );
    }

    public String product_sku_insert() {
        return insert(ProductSkuEntity.class,
                "productId", "code", "name", "description", "status"
        );
    }

    public String product_sku_update() {
        return update(ProductSkuEntity.class, "id",
                "productId", "code", "name", "description", "status"
        );
    }

    public String division_update() {
        return update(DivisionEntity.class, "id",
                "name", "code", "level", "parentId"
                );
    }

    public static String insert(Class entityClass, String... includes) {
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ").append(resolveTable(entityClass)).append("(");
        sql.append(Stream.of(includes).collect(Collectors.joining(","))).append(") values(");
        sql.append(Stream.of(includes).map(s -> "#{"+s+"}").collect(Collectors.joining(","))).append(")");
        return sql.toString();
    }

    public static String update(Class entityClass, String id, String... updateFields) {
        StringBuilder sql = new StringBuilder();
        sql.append("update").append(resolveTable(entityClass)).append(" set ");
        sql.append(Stream.of(updateFields).map(field -> field+"=#{"+field+"}")
                .collect(Collectors.joining(",")))
                .append(" where ").append(id).append("=#{").append(id).append("}");
        return sql.toString();
    }

    private static String resolveTable(Class entityClass) {
        String name = entityClass.getSimpleName();
        if (name.endsWith("Entity")) {
            name = name.substring(0, name.length() - "Entity".length());
        }
        return name;
    }
}
