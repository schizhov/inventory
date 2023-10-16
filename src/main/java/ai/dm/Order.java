package ai.dm;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record Order(
        @JsonProperty("orderId") @ColumnName("order_id") String orderId,
        @JsonProperty("productId") @ColumnName("product_id") String productId,
        @JsonProperty("currency") @ColumnName("currency") String currency,
        @JsonProperty("quantity") @ColumnName("quantity") int quantity,
        @JsonProperty("shippingCost") @ColumnName("shipping_cost") BigDecimal shippingCost,
        @JsonProperty("amount") @ColumnName("amount") BigDecimal amount,
        @JsonProperty("channel") @ColumnName("channel") String channel,
        @JsonProperty("channelGroup") @ColumnName("channel_group") String channelGroup,
        @JsonProperty("timestamp")
        @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
        @ColumnName("timestamp") LocalDateTime timestamp
) {
}
