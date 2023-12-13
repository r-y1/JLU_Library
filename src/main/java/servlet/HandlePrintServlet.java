package servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entity.Order;
import entity.OrderItem;

@WebServlet("/HandlePrint")
public class HandlePrintServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession();
		resp.setContentType("text/html;charset=utf-8");
		List<Object> list = (List<Object>)session.getAttribute("value");
		//resp.getWriter().write(list.toString());
		File file = new File("D:\\吉林大学\\大四上\\软件工程案列分析与实践\\superMarket\\orderPrint\\order.txt");

		if(!file.exists()) {
			file.createNewFile();
		}
		FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fileWriter);

		bw.write("借阅信息:");
		for(Object object : list) {
			String line = "";
			String l = "";
			Order order = (Order)object;
			line = line + "ID:" + order.getId() + "  " + "总价:" + order.getTotal() + "  ";
			for(OrderItem orderItem : order.getItemList()) {
				l += orderItem.getPrice() + "*" + orderItem.getAmount() + "份" + orderItem.getGoodsName() + " ";
			}
			line += "图书详情:";
			line += l;
			line += "  ";
			
			line = line + "借阅信息:[" + "借阅名:" + order.getName() + "/" + "电话:" + order.getPhone() + "/" + "地址:" + order.getAddress() + "]  ";
			line += "借阅状态:" ;
			if(order.getStatus() == 2)
				line += "已支付";
			else if(order.getStatus() == 3)
				line += "已发货";
			else if(order.getStatus() == 4)
				line += "已完成";
			
			line += "  ";
			line += "支付方式:";
			if(order.getPaytype() == 1)
				line += "微信";
			else if(order.getPaytype() == 2)
				line += "支付宝";
			else if(order.getPaytype() == 3)
				line += "货到付款";
			line += "  ";
			
			line += "下单用户:";
			line += order.getUser().getUsername() + "  ";
			
			line += "下单时间:";
			line += order.getDatetime();

			bw.newLine();
			bw.write(line);
		}
		
		
		
		bw.close();
		
//		resp.getWriter().write("订单已打印");
		req.getRequestDispatcher("/admin/order_list").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(req, resp);
	}
	
	
}
