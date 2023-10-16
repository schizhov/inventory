package ai.dm;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

public record Product(
        @JsonProperty("id") @ColumnName("id") String id,
        @JsonProperty("name") @ColumnName("name") String name,
        @JsonProperty("quantity") @ColumnName("quantity") int quantity,
        @JsonProperty("category") @ColumnName("category") String category,
        @JsonProperty("subCategory") @ColumnName("sub_category") String subCategory
) {
}
