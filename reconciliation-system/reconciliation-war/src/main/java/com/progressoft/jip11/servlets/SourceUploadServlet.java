package com.progressoft.jip11.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class SourceUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        session.setAttribute("sourceName", req.getParameter("source_name"));
        session.setAttribute("sourceType", req.getParameter("source_type"));
        session.setAttribute("sourceFile", req.getParameter("source_file"));
        uploadFile(req);
        req.getRequestDispatcher("/WEB-INF/target-upload.html").forward(req, resp);
    }

    private void uploadFile(HttpServletRequest req) throws IOException, ServletException {
        Part part = req.getPart("source_file");
        String fileName = part.getSubmittedFileName();
        for (Part p : req.getParts())
            p.write(fileName);
    }
}
