package com.epam.esm.model.dto;

import com.epam.esm.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagDto extends RepresentationModel<TagDto> {
    @NotNull
    @Size(min = 3, max = 100, message = "name length should contain from 1 to 100 symbols")
    private String name;
    private int id;
}
