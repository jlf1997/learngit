package com.cimr.sysmanage.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cimr.comm.aop.FullPage;



@Controller
@RequestMapping({"/simulate"})
public class SimulateController
{
  @FullPage(menu="")
  @RequestMapping({"index"})
  public ModelAndView index(HttpServletRequest req, HttpServletResponse resp, ModelMap data)
  {
    return new ModelAndView("comm/login/login", data);
  }
}
