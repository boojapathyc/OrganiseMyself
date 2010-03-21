package com.rajiv.intelligence.web;

import com.rajiv.intelligence.graph.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SimpleController extends MultiActionController {

    private GraphAnalyzer graphAnalyzer;
    private IntelligentGraph intelligentGraph;


    public SimpleController(GraphAnalyzer graphAnalyzer, IntelligentGraph intelligentGraph) {
        this.graphAnalyzer = graphAnalyzer;
        this.intelligentGraph = intelligentGraph;
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

    private ModelAndView writeAndRedirect(HttpServletRequest request, String currentPageId) {
        writeNavigationInformation(request, currentPageId);
        return new ModelAndView(currentPageId + ".jsp");
    }

    private void writeNavigationInformation(HttpServletRequest request, String currentPageId) {
        System.out.println("page i am at :" + currentPageId);
        ActivityNode sourceNode = null;
        RequestData sourceData;
        String sourcePage = request.getParameter("sourcePage");
        if (sourcePage != null) {
            sourceData = requestData(sourcePage);
            sourceNode = new ActivityNode(sourceData);
        }
        ActivityNode visited = activityNode(currentPageId);
        intelligentGraph.addNode(sourceNode, visited);
        NavigationPath path = graphAnalyzer.getMaxNavigationPath(intelligentGraph, visited);
        if (path != null)
            System.out.println("i usually go to this place from here :" + intelligentGraph.getDest(path).getRequestData().getId());
        System.out.println("here   " + intelligentGraph.getVertices().size());
    }

    private ActivityNode activityNode(String id) {
        return new ActivityNode(requestData(id));
    }

    private RequestData requestData(String id) {
        return new RequestData(id);
    }
}