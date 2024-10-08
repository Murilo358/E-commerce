/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.order.service.coreapi.events.order;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class OrderCreatedEvent extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 8635470654234471426L;


  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"OrderCreatedEvent\",\"namespace\":\"com.order.service.coreapi.events.order\",\"fields\":[{\"name\":\"products\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"OrderProductState\",\"fields\":[{\"name\":\"productId\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"name\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"description\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"price\",\"type\":\"double\"},{\"name\":\"sellerId\",\"type\":\"long\"},{\"name\":\"categoryId\",\"type\":{\"type\":\"string\",\"logicalType\":\"uuid\"}},{\"name\":\"inventoryCount\",\"type\":\"int\"},{\"name\":\"createdAt\",\"type\":[\"null\",{\"type\":\"int\",\"logicalType\":\"date\"}],\"default\":null},{\"name\":\"updatedAt\",\"type\":[\"null\",{\"type\":\"int\",\"logicalType\":\"date\"}],\"default\":null},{\"name\":\"deletedAt\",\"type\":[\"null\",{\"type\":\"int\",\"logicalType\":\"date\"}],\"default\":null}]}}},{\"name\":\"payment_method\",\"type\":\"long\"},{\"name\":\"created_at\",\"type\":{\"type\":\"int\",\"logicalType\":\"date\"}}],\"version\":\"1\"}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static final SpecificData MODEL$ = new SpecificData();
  static {
    MODEL$.addLogicalTypeConversion(new org.apache.avro.data.TimeConversions.DateConversion());
    MODEL$.addLogicalTypeConversion(new org.apache.avro.Conversions.UUIDConversion());
  }

  private static final BinaryMessageEncoder<OrderCreatedEvent> ENCODER =
      new BinaryMessageEncoder<>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<OrderCreatedEvent> DECODER =
      new BinaryMessageDecoder<>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<OrderCreatedEvent> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<OrderCreatedEvent> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<OrderCreatedEvent> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this OrderCreatedEvent to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a OrderCreatedEvent from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a OrderCreatedEvent instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static OrderCreatedEvent fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  private java.util.List<com.order.service.coreapi.events.order.OrderProductState> products;
  private long payment_method;
  private java.time.LocalDate created_at;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public OrderCreatedEvent() {}

  /**
   * All-args constructor.
   * @param products The new value for products
   * @param payment_method The new value for payment_method
   * @param created_at The new value for created_at
   */
  public OrderCreatedEvent(java.util.List<com.order.service.coreapi.events.order.OrderProductState> products, java.lang.Long payment_method, java.time.LocalDate created_at) {
    this.products = products;
    this.payment_method = payment_method;
    this.created_at = created_at;
  }

  @Override
  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }

  @Override
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }

  // Used by DatumWriter.  Applications should not call.
  @Override
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return products;
    case 1: return payment_method;
    case 2: return created_at;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  private static final org.apache.avro.Conversion<?>[] conversions =
      new org.apache.avro.Conversion<?>[] {
      null,
      null,
      new org.apache.avro.data.TimeConversions.DateConversion(),
      null
  };

  @Override
  public org.apache.avro.Conversion<?> getConversion(int field) {
    return conversions[field];
  }

  // Used by DatumReader.  Applications should not call.
  @Override
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: products = (java.util.List<com.order.service.coreapi.events.order.OrderProductState>)value$; break;
    case 1: payment_method = (java.lang.Long)value$; break;
    case 2: created_at = (java.time.LocalDate)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'products' field.
   * @return The value of the 'products' field.
   */
  public java.util.List<com.order.service.coreapi.events.order.OrderProductState> getProducts() {
    return products;
  }


  /**
   * Sets the value of the 'products' field.
   * @param value the value to set.
   */
  public void setProducts(java.util.List<com.order.service.coreapi.events.order.OrderProductState> value) {
    this.products = value;
  }

  /**
   * Gets the value of the 'payment_method' field.
   * @return The value of the 'payment_method' field.
   */
  public long getPaymentMethod() {
    return payment_method;
  }


  /**
   * Sets the value of the 'payment_method' field.
   * @param value the value to set.
   */
  public void setPaymentMethod(long value) {
    this.payment_method = value;
  }

  /**
   * Gets the value of the 'created_at' field.
   * @return The value of the 'created_at' field.
   */
  public java.time.LocalDate getCreatedAt() {
    return created_at;
  }


  /**
   * Sets the value of the 'created_at' field.
   * @param value the value to set.
   */
  public void setCreatedAt(java.time.LocalDate value) {
    this.created_at = value;
  }

  /**
   * Creates a new OrderCreatedEvent RecordBuilder.
   * @return A new OrderCreatedEvent RecordBuilder
   */
  public static com.order.service.coreapi.events.order.OrderCreatedEvent.Builder newBuilder() {
    return new com.order.service.coreapi.events.order.OrderCreatedEvent.Builder();
  }

  /**
   * Creates a new OrderCreatedEvent RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new OrderCreatedEvent RecordBuilder
   */
  public static com.order.service.coreapi.events.order.OrderCreatedEvent.Builder newBuilder(com.order.service.coreapi.events.order.OrderCreatedEvent.Builder other) {
    if (other == null) {
      return new com.order.service.coreapi.events.order.OrderCreatedEvent.Builder();
    } else {
      return new com.order.service.coreapi.events.order.OrderCreatedEvent.Builder(other);
    }
  }

  /**
   * Creates a new OrderCreatedEvent RecordBuilder by copying an existing OrderCreatedEvent instance.
   * @param other The existing instance to copy.
   * @return A new OrderCreatedEvent RecordBuilder
   */
  public static com.order.service.coreapi.events.order.OrderCreatedEvent.Builder newBuilder(com.order.service.coreapi.events.order.OrderCreatedEvent other) {
    if (other == null) {
      return new com.order.service.coreapi.events.order.OrderCreatedEvent.Builder();
    } else {
      return new com.order.service.coreapi.events.order.OrderCreatedEvent.Builder(other);
    }
  }

  /**
   * RecordBuilder for OrderCreatedEvent instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<OrderCreatedEvent>
    implements org.apache.avro.data.RecordBuilder<OrderCreatedEvent> {

    private java.util.List<com.order.service.coreapi.events.order.OrderProductState> products;
    private long payment_method;
    private java.time.LocalDate created_at;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$, MODEL$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.order.service.coreapi.events.order.OrderCreatedEvent.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.products)) {
        this.products = data().deepCopy(fields()[0].schema(), other.products);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.payment_method)) {
        this.payment_method = data().deepCopy(fields()[1].schema(), other.payment_method);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.created_at)) {
        this.created_at = data().deepCopy(fields()[2].schema(), other.created_at);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
    }

    /**
     * Creates a Builder by copying an existing OrderCreatedEvent instance
     * @param other The existing instance to copy.
     */
    private Builder(com.order.service.coreapi.events.order.OrderCreatedEvent other) {
      super(SCHEMA$, MODEL$);
      if (isValidValue(fields()[0], other.products)) {
        this.products = data().deepCopy(fields()[0].schema(), other.products);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.payment_method)) {
        this.payment_method = data().deepCopy(fields()[1].schema(), other.payment_method);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.created_at)) {
        this.created_at = data().deepCopy(fields()[2].schema(), other.created_at);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'products' field.
      * @return The value.
      */
    public java.util.List<com.order.service.coreapi.events.order.OrderProductState> getProducts() {
      return products;
    }


    /**
      * Sets the value of the 'products' field.
      * @param value The value of 'products'.
      * @return This builder.
      */
    public com.order.service.coreapi.events.order.OrderCreatedEvent.Builder setProducts(java.util.List<com.order.service.coreapi.events.order.OrderProductState> value) {
      validate(fields()[0], value);
      this.products = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'products' field has been set.
      * @return True if the 'products' field has been set, false otherwise.
      */
    public boolean hasProducts() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'products' field.
      * @return This builder.
      */
    public com.order.service.coreapi.events.order.OrderCreatedEvent.Builder clearProducts() {
      products = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'payment_method' field.
      * @return The value.
      */
    public long getPaymentMethod() {
      return payment_method;
    }


    /**
      * Sets the value of the 'payment_method' field.
      * @param value The value of 'payment_method'.
      * @return This builder.
      */
    public com.order.service.coreapi.events.order.OrderCreatedEvent.Builder setPaymentMethod(long value) {
      validate(fields()[1], value);
      this.payment_method = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'payment_method' field has been set.
      * @return True if the 'payment_method' field has been set, false otherwise.
      */
    public boolean hasPaymentMethod() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'payment_method' field.
      * @return This builder.
      */
    public com.order.service.coreapi.events.order.OrderCreatedEvent.Builder clearPaymentMethod() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'created_at' field.
      * @return The value.
      */
    public java.time.LocalDate getCreatedAt() {
      return created_at;
    }


    /**
      * Sets the value of the 'created_at' field.
      * @param value The value of 'created_at'.
      * @return This builder.
      */
    public com.order.service.coreapi.events.order.OrderCreatedEvent.Builder setCreatedAt(java.time.LocalDate value) {
      validate(fields()[2], value);
      this.created_at = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'created_at' field has been set.
      * @return True if the 'created_at' field has been set, false otherwise.
      */
    public boolean hasCreatedAt() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'created_at' field.
      * @return This builder.
      */
    public com.order.service.coreapi.events.order.OrderCreatedEvent.Builder clearCreatedAt() {
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public OrderCreatedEvent build() {
      try {
        OrderCreatedEvent record = new OrderCreatedEvent();
        record.products = fieldSetFlags()[0] ? this.products : (java.util.List<com.order.service.coreapi.events.order.OrderProductState>) defaultValue(fields()[0]);
        record.payment_method = fieldSetFlags()[1] ? this.payment_method : (java.lang.Long) defaultValue(fields()[1]);
        record.created_at = fieldSetFlags()[2] ? this.created_at : (java.time.LocalDate) defaultValue(fields()[2]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<OrderCreatedEvent>
    WRITER$ = (org.apache.avro.io.DatumWriter<OrderCreatedEvent>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<OrderCreatedEvent>
    READER$ = (org.apache.avro.io.DatumReader<OrderCreatedEvent>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}










