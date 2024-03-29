package com.atomscat.freeswitch.xml.domain.directory.param;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Params implements Serializable {
    @JacksonXmlElementWrapper(localName = "param", useWrapping = false)
    private List<Param> param;
}
