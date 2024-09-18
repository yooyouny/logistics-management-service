package com.sparta.delivery.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDelivery is a Querydsl query type for Delivery
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDelivery extends EntityPathBase<Delivery> {

    private static final long serialVersionUID = 1807979663L;

    public static final QDelivery delivery = new QDelivery("delivery");

    public final com.sparta.commons.domain.jpa.QBaseEntity _super = new com.sparta.commons.domain.jpa.QBaseEntity(this);

    public final ComparablePath<java.util.UUID> arrivalHubId = createComparable("arrivalHubId", java.util.UUID.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final StringPath deletedBy = _super.deletedBy;

    public final ComparablePath<java.util.UUID> deliveryId = createComparable("deliveryId", java.util.UUID.class);

    public final EnumPath<State.DeliveryState> deliveryState = createEnum("deliveryState", State.DeliveryState.class);

    public final ComparablePath<java.util.UUID> departureHubId = createComparable("departureHubId", java.util.UUID.class);

    public final NumberPath<Double> estimatedDistance = createNumber("estimatedDistance", Double.class);

    public final NumberPath<Long> estimatedElapsedTime = createNumber("estimatedElapsedTime", Long.class);

    public final BooleanPath isDelete = createBoolean("isDelete");

    public final ComparablePath<java.util.UUID> orderId = createComparable("orderId", java.util.UUID.class);

    public final ListPath<DeliveryRoute, QDeliveryRoute> routes = this.<DeliveryRoute, QDeliveryRoute>createList("routes", DeliveryRoute.class, QDeliveryRoute.class, PathInits.DIRECT2);

    public final StringPath shippingAddress = createString("shippingAddress");

    public final DateTimePath<java.time.LocalDateTime> shippingEndDate = createDateTime("shippingEndDate", java.time.LocalDateTime.class);

    public final ComparablePath<java.util.UUID> shippingManagerId = createComparable("shippingManagerId", java.util.UUID.class);

    public final StringPath shippingManagerSlackId = createString("shippingManagerSlackId");

    public final DateTimePath<java.time.LocalDateTime> shippingStartDate = createDateTime("shippingStartDate", java.time.LocalDateTime.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QDelivery(String variable) {
        super(Delivery.class, forVariable(variable));
    }

    public QDelivery(Path<? extends Delivery> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDelivery(PathMetadata metadata) {
        super(Delivery.class, metadata);
    }

}

