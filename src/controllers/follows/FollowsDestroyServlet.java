package controllers.follows;

import java.io.IOException;

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
 * Servlet implementation class FollowsDestroyServlet
 */
@WebServlet("/follows/destroy")
public class FollowsDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FollowsDestroyServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String _token = (String) request.getParameter("_token");
        if (_token != null && _token.equals(request.getSession().getId())) {

            EntityManager em = DBUtil.createEntityManager();

            // ログイン情報
            Employee loginEmployee = (Employee) request.getSession().getAttribute("login_employee");
            // フォローする従業員
            Employee emp = new Employee();

            emp.setId(Integer.parseInt(request.getParameter("followEmployee")));
            Follow f = (Follow) em.createNamedQuery("getFollows", Follow.class)
                    .setParameter("employeeId", loginEmployee.getId())
                    .setParameter("followEmployee", emp)
                    .getSingleResult();

            em.getTransaction().begin();
            em.remove(f);
            em.getTransaction().commit();
            em.close();

            request.getSession().setAttribute("flush", "フォロー解除しました。");
            response.sendRedirect(request.getContextPath() + "/employees/index");
        }
    }
}
