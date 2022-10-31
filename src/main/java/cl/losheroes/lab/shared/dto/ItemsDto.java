package cl.losheroes.lab.shared.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemsDto<T> {

    private List<T> items;
    private Long totalItems;
    private Integer totalPages;
    private Integer page;
    private Integer itemsPerPage;

}
