package com.atomscat.freeswitch.xml.domain.directory.group;

import com.atomscat.freeswitch.xml.domain.directory.user.Users;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Group implements Serializable {

    @JacksonXmlProperty(localName = "users", isAttribute = true)
    private Users users;
}
