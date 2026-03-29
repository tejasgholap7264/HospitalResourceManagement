package com.jaivyroy.hospotalManagement.dto.SIngUpDto;

import com.jaivyroy.hospotalManagement.entity.Type.RoolTYPE;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingRequestDto {

    private String username;
    private String password;
    private String name ;
private  Set<RoolTYPE>roolTYPES = new HashSet<>() ;  // just for the under standing the process for
    // recomment to dont use this in the real application at the time of the sighup user can define there own rool
}