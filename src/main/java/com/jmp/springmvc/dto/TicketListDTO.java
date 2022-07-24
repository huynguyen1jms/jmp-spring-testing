package com.jmp.springmvc.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="tickets")
public class TicketListDTO {

    @XmlElement(name = "ticket", type = TicketDTO.class)
    private List<TicketDTO> student;

    public List<TicketDTO> getStudent() {
        return student;
    }

    public void setStudent(List<TicketDTO> student) {
        this.student = student;
    }
}
