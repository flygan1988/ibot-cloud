package com.taiping.ibot.server.knowledge.entity.kg;

import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@NodeEntity(label = "Product")
@Data
@ToString
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @Property(name = "sale_channel")
    private String saleChannel;

    @Property(name = "prod_type")
    private String prodType;

    @Property(name = "raw_name")
    private String productRawName;

    @Property(name = "internal_id")
    private String internalId;

    @Property(name = "sale_status")
    private String saleStatus;

    @Property(name = "list_area")
    private String listArea;

    @Property(name = "prod_brief")
    private String prodBrief;

    @Property(name = "product_abbr")
    private String productAbbr;

    @Property(name = "new_busvalue_table")
    private String newBusvalueTable;

    @Property(name = "commission_table")
    private String commissionTable;

    @Property(name = "is_main")
    private String isMain;

    @Property(name = "product_id")
    private String productId;

    @Property(name = "name")
    private String productName;

    @Property(name = "fold_coef")
    private String foldCoef;

    @Property(name = "tariff_table")
    private String tariffTable;

    @Property(name = "start_date")
    private String startDate;
}
