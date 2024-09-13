package com.sparta.user.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QShippingManager is a Querydsl query type for ShippingManager
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShippingManager extends EntityPathBase<ShippingManager> {

    private static final long serialVersionUID = 708119981L;

    public static final QShippingManager shippingManager = new QShippingManager("shippingManager");

    public final com.sparta.commons.domain.jpa.QBaseEntity _super = new com.sparta.commons.domain.jpa.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final StringPath deletedBy = _super.deletedBy;

    public final ComparablePath<java.util.UUID> hubId = createComparable("hubId", java.util.UUID.class);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final BooleanPath isDelete = createBoolean("isDelete");

    public final StringPath slackId = createString("slackId");

    public final EnumPath<com.sparta.user.domain.model.vo.ShippingManagerType> type = createEnum("type", com.sparta.user.domain.model.vo.ShippingManagerType.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QShippingManager(String variable) {
        super(ShippingManager.class, forVariable(variable));
    }

    public QShippingManager(Path<? extends ShippingManager> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShippingManager(PathMetadata metadata) {
        super(ShippingManager.class, metadata);
    }

}

