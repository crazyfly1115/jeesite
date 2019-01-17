/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.bean.Ret;
import com.thinkgem.jeesite.common.beanvalidator.BeanValidators;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.utils.excel.ImportExcel;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Area;
import com.thinkgem.jeesite.modules.sys.entity.User;
import com.thinkgem.jeesite.modules.sys.service.AreaService;
import com.thinkgem.jeesite.modules.sys.service.SystemService;
import com.thinkgem.jeesite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * 区域Controller
 * @author ThinkGem
 * @version 2013-5-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/upload")
public class UploadController extends BaseController {

	@RequiresPermissions("user")
	@RequestMapping(value = "upload", method=RequestMethod.POST)
	@ResponseBody
	public String uploadtFile(MultipartFile file,String toText,RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			return new Ret(1,"演示模式无法操作!").toString();
		}
		if(file.isEmpty()) {
			throw new RuntimeException("上传文件不能为空");
		}
		//上传文件名
		String filename = file.getOriginalFilename();
		Calendar date = Calendar.getInstance();
		File filepath = new File( Global.getUserfilesBaseDir(),filename);
		//判断路径是否存在，如果不存在就创建一个
		if (!filepath.getParentFile().exists()) {
			filepath.getParentFile().mkdirs();
		}
		//将上传文件保存到一个目标文件当中
		String newfileName=DateUtils.getDate("yyyyMMddHHmmssSSS")+filename.substring(filename.lastIndexOf("."),filename.length());
		String newpath=date.get(Calendar.YEAR) + File.separator + (date.get(Calendar.MONTH)+1) + File.separator+ date.get(Calendar.DAY_OF_MONTH)+File.separator;
		File newfile=new File(Global.getUserfilesBaseDir() + File.separator +newpath);
		if(!newfile.exists()){
			newfile.mkdirs();
		}
		String path=Global.getUserfilesBaseDir() + File.separator +newpath+ newfileName;
		try {

			file.transferTo(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("IO",e);
			throw  new RuntimeException("IO错误");
		}
		Ret ret=new Ret(0, "上传文件成功");
		if("0".equals(toText)){
			String rs=StringUtils.readToString(path);
			ret.putMap("text",rs);
		}

		ret.putMap("url",   newpath.replaceAll("\\\\","/")+ newfileName);
		ret.putMap("serverurl", Global.getUserfilesWebUrl());
		return ret.toString();
	}
}
