package com.thinkgem.jeesite.common.utils.Vuetree;

import java.util.List;

	public class VueTreeDTO {  
		  String label;  
		  String parentId;  
		  String id;
		  String url;
		  String icon;
		  String isBotton;
		  List<VueTreeDTO > children;

		public  VueTreeDTO(){

		}
		  
		public VueTreeDTO(String label, String parentId, String id,String url,String icon,String type) {
			super();
			this.label = label;
			this.parentId = parentId;
			this.id = id;
			this.url=url;
			this.icon=icon;
		}
		public String getLabel() {
			return label;
		}
		public void setLabel(String label) {
			this.label = label;
		}
		public String getParentId() {
			return parentId;
		}
		public void setParentId(String parentId) {
			this.parentId = parentId;
		}
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public List<VueTreeDTO> getChildren() {
			return children;
		}
		public void setChildren(List<VueTreeDTO> children) {
			this.children = children;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}

		public String getIsBotton() {
			return isBotton;
		}

		public void setIsBotton(String isBotton) {
			this.isBotton = isBotton;
		}
	}
