package com.sparta.commons.domain.jpa;
public class KafkaTopicConstant {
  public static final String DEDUCT_PRODUCT_QUANTITY = "deduct_product_quantity";
  public static final String ERROR_IN_DEDUCT_PRODUCT_QUANTITY = "error_in_deduct_product_quantity";
  public static final String CREATE_DELIVERY = "create_delivery";
  public static final String ERROR_IN_CREATE_DELIVERY = "error_in_create_delivery";
  public static final String DELETE_DELIVERY = "delete_delivery";
  public static final String REVERT_PRODUCT_QUANTITY = "revert_product_quantity";

  private KafkaTopicConstant() {
  }
}