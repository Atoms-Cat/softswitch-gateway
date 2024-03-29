package com.atomscat.freeswitch.xml.domain.directory.variable;

import com.atomscat.freeswitch.xml.domain.directory.param.Param;
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
public class Variables implements Serializable {
    @JacksonXmlElementWrapper(localName = "variable", useWrapping = false)
    private List<Variable> variable;

}
