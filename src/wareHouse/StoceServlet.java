package wareHouse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.WareHouse;

import net.sf.json.JSONArray;

public class StoceServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public StoceServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	private StoceService ss = new StoceService();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		if (!request.getParameter("wID").equals("--请选择--")) {
			// 查询仓库信息
			Integer wID = Integer.parseInt(request.getParameter("wID"));
			List listWH = ss.selectWH(wID);
			
			// 查询所有汽车品牌
			List list = ss.selectCarName(wID);
			request.getSession().setAttribute("cNames", list);
			List listAll = new ArrayList();
			listAll.add(listWH);
			listAll.add(list);
			JSONArray ja = JSONArray.fromObject(listAll);
			response.getWriter().print(ja);

		}

	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (!request.getParameter("wID").equals("--请选择--")) {
			Integer wID = Integer.parseInt(request.getParameter("wID"));
			List listWH = ss.selectWH(wID);
			WareHouse wh = (WareHouse) listWH.get(0);
			request.getSession().setAttribute("selectWH", wh);
			String cName = request.getParameter("select2");
			String cModel = request.getParameter("textfield");
			
			List listCars = ss.carWh(wID,cName,cModel);
			request.getSession().setAttribute("cars", listCars);
		}
		request.getRequestDispatcher("/warehouse/stoce.jsp").forward(request, response);

	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
