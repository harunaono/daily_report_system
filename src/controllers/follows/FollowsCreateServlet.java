package controllers.follows;

import java.io.IOException;
import java.sql.Timestamp;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Follow;
import utils.DBUtil;

/**
 * Servlet implementation class FollowsCreateServlet
 */
@WebServlet("/follows/create")
public class FollowsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String _token = (String) request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId()));

        EntityManager em = DBUtil.createEntityManager();

        Follow f = new Follow();

        //ログイン者のID
        Employee loginEmp = (Employee) request.getSession().getAttribute("login_employee");
        f.setEmployeeId(loginEmp.getId());

        //フォローする人の情報
        int id = Integer.parseInt(request.getParameter("followEmployee"));
        Employee Emp = new Employee();
        Emp.setId(id);
        f.setEmployee(Emp);

        //f.setEmployee((Employee) request.getSession().getAttribute("login_employee"));


        //f.setEmployee_id(request.getParameter("employee_id"));
        //f.setFollow_Employee_id(request.getParameter("follow_employee_id"));

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        f.setCreated_at(currentTime);
        f.setUpdated_at(currentTime);

        em.getTransaction().begin();
        em.persist(f);
        em.getTransaction().commit();
        em.close();

        request.getSession().setAttribute("flush", "フォローしました。");

            response.sendRedirect(request.getContextPath() + "/employees/index");

    }
}