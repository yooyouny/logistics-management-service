package com.sparta.delivery.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeliveryRoute is a Querydsl query type for DeliveryRoute
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliveryRoute extends EntityPathBase<DeliveryRoute> {

    private static final long serialVersionUID = -65687814L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeliveryRoute deliveryRoute = new QDeliveryRoute("deliveryRoute");

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

    public final QDelivery delivery;

    public final ComparablePath<java.util.UUID> deliveryRouteId = createComparable("deliveryRouteId", java.util.UUID.class);

    public final ComparablePath<java.util.UUID> departureHubId = createComparable("departureHubId", java.util.UUID.class);

    public final NumberPath<Double> estimatedDistance = createNumber("estimatedDistance", Double.class);

    public final NumberPath<Long> estimatedElapsedTime = createNumber("estimatedElapsedTime", Long.class);

    public final NumberPath<Integer> realDistance = createNumber("realDistance", Integer.class);

    public final ComparablePath<java.time.Duration> realElapsedTime = createComparable("realElapsedTime", java.time.Duration.class);

    public final EnumPath<State.RouteState> routeState = createEnum("routeState", State.RouteState.class);

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QDeliveryRoute(String variable) {
        this(DeliveryRoute.class, forVariable(variable), INITS);
    }

    public QDeliveryRoute(Path<? extends DeliveryRoute> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeliveryRoute(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeliveryRoute(PathMetadata metadata, PathInits inits) {
        this(DeliveryRoute.class, metadata, inits);
    }

    public QDeliveryRoute(Class<? extends DeliveryRoute> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.delivery = inits.isInitialized("delivery") ? new QDelivery(forProperty("delivery")) : null;
    }

}

