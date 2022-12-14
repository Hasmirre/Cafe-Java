/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Item;

public class AddToCart extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {

            /*if (request.getSession().getAttribute("username") == null) {
                response.sendRedirect("login.jsp");
                return;
            }*/

            //Create Item object from User request
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            if (quantity == 0) {
                response.sendRedirect("invalid-quantity.jsp");
                return;
            }

            int id = Integer.parseInt(request.getParameter("item_id"));
            Item item = new Item(id);
            item.setQuantity(quantity);

            //Retrieve Cart items from session and add item to it
            ArrayList<Item> itemList = new ArrayList<>();
            HttpSession session = request.getSession();
            ArrayList<Item> existingList = (ArrayList<Item>) session.getAttribute("CartList");

            if (existingList == null) {
                itemList.add(item);
                session.setAttribute("CartList", itemList);
                response.sendRedirect("menu.jsp");
            } else {
                //Allows duplicates
                itemList = existingList;
                boolean exist = false;
                for (Item x : itemList) {
                    if (x.getId() == id) {
                        exist = true;
                        x.setQuantity(x.getQuantity() + quantity);
                        response.sendRedirect("menu.jsp");
                    }
                }
                if (!exist) {
                    itemList.add(item);
                    response.sendRedirect("menu.jsp");
                }

            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
