package com.thinkgem.jeesite.common.utils.Vuetree;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {
	//循环方式将list展现为tree
	public static List<VueTreeDTO> List2Tree(List<VueTreeDTO> DTOList){
		ArrayList<VueTreeDTO> nodeList=new ArrayList<VueTreeDTO>();
		for(VueTreeDTO node1 : DTOList){//taskDTOList 是数据库获取的List列表数据或者来自其他数据源的List  
            boolean mark = false;  
            for(VueTreeDTO node2 : DTOList){  
                if(node1.getParentId()!=null && node1.getParentId().equals(node2.getId())){  
                    mark = true;  
                    if(node2.getChildren() == null)  
                        node2.setChildren(new ArrayList<VueTreeDTO>());  
                        node2.getChildren().add(node1);  
                    break;  
                }  
            }  
            if(!mark){  
                nodeList.add(node1);  
            }  
        }  
		return nodeList;
	}
}
