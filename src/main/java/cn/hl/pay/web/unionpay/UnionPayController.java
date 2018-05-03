package cn.hl.pay.web.unionpay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import cn.hl.pay.constants.unionpay.PayWay;
import cn.hl.pay.domain.model.Order;
import cn.hl.pay.service.unionpay.UnionpayService;
import cn.hl.pay.util.unionpay.sdk.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "unionpay")
@Controller
@RequestMapping("/unionpay")
public class UnionPayController {

  @Autowired
  private UnionpayService payService;

  @ApiOperation(value = "Index page of unionpay")
  @RequestMapping(value = "/index", method = RequestMethod.GET)
  public String index() {
    return "unionpay/index";
  }

  @ApiOperation(value = "PC pay")
  @RequestMapping(value = "/pcpay", method = RequestMethod.POST)
  public ModelAndView pcpay(Order order) {
    LogUtil.writeLog("order:" + order);
    ModelAndView mv = new ModelAndView("unionpay/pay");
    order.setPayWay(PayWay.PC.code());
    String form = payService.getForm(order);
    LogUtil.writeLog("form:" + form);
    mv.addObject("form", form);
    return mv;
  }

}
