package com.rajiv.intelligence.web;

import com.rajiv.intelligence.graph.GraphController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class SimpleController extends MultiActionController {

    private GraphController graphController;

    public SimpleController(GraphController graphController) {
        this.graphController = graphController;
    }

    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return writeAndRedirect(request, "index");
    }

    public ModelAndView page1(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return writeAndRedirect(request, "page1");
    }

    public ModelAndView page2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return writeAndRedirect(request, "page2");
    }

    public ModelAndView page3(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return writeAndRedirect(request, "page3");
    }

    public ModelAndView page4(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return writeAndRedirect(request, "page4");
    }

    public ModelAndView page5(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return writeAndRedirect(request, "page5");
    }

    private ModelAndView writeAndRedirect(HttpServletRequest request, String currentPageId) {
        String sourcePage = request.getParameter("sourcePage");
        if (sourcePage == null) sourcePage = "index";
        String mostNavigatedPage = graphController.maxNavigatedPage(sourcePage, currentPageId);
        Map<String, Object> analysisMap = new HashMap<String, Object>();
        analysisMap.put("MOST_NAV_PAGE", mostNavigatedPage);
        analysisMap.put("SOURCE_PAGE", currentPageId);
        analysisMap.put("TARGET_PAGES", graphController.createTargetPathsFromGraph(currentPageId));
        return new ModelAndView(currentPageId + ".jsp", analysisMap);
    }
}