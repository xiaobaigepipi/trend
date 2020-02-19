package com.lxh.trend.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.lxh.trend.client.IndexDataClient;
import com.lxh.trend.pojo.AnnualProfit;
import com.lxh.trend.pojo.IndexData;
import com.lxh.trend.pojo.Profit;
import com.lxh.trend.pojo.Trade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/*
 * @PackageName: com.lxh.trend.service
 * @ClassName: BackTestService
 * @Description:
 * @author: 辉
 * @date: 2020/2/19 0:07
 * */
@Service
public class BackTestService {
    @Autowired IndexDataClient indexDataClient;

    public List<IndexData> listIndexData(String code){
        List<IndexData> result = indexDataClient.getIndexData(code);
        Collections.reverse(result);

//      for (IndexData indexData : result) {
//          System.out.println(indexData.getDate());
//      }

        return result;
    }
    /*
     * @Author 辉
     * @Description //TODO
     * @Date 16:37 2020/2/19
     * @Param [ma, sellRate, buyRate, serviceCharge, indexDatas]
     * 前20日股票价值均线， 出售阈值， 购买阈值， 手续费，数据
     * @return java.util.Map<java.lang.String,java.lang.Object>
     **/
    public Map<String,Object> simulate(int ma, float sellRate, float buyRate, float serviceCharge, List<IndexData> indexDatas)  {

        List<Profit> profits =new ArrayList<>();
        List<Trade> trades = new ArrayList<>();

        float initCash = 1000; //初始值，比如你持1000元开始购买股票
        float cash = initCash; //卖出股票所得钱
        float share = 0; //购买份额
        float value = 0; //最后价值多少

        int winCount = 0; //盈利交易次数
        float totalWinRate = 0;//总共盈利率
        float avgWinRate = 0; //平均盈利比率
        float totalLossRate = 0; //亏损率
        int lossCount = 0; //亏损次数
        float avgLossRate = 0; //平均亏损比率

        float init =0;
        if(!indexDatas.isEmpty())
            init = indexDatas.get(0).getClosePoint();

        for (int i = 0; i<indexDatas.size() ; i++) {
            IndexData indexData = indexDatas.get(i);
            float closePoint = indexData.getClosePoint();
            float avg = getMA(i,ma,indexDatas);
            float max = getMax(i,ma,indexDatas);

            float increase_rate = closePoint/avg;
            float decrease_rate = closePoint/max;

            if(avg!=0) {
                //buy 超过了均线
                if(increase_rate>buyRate  ) {
                    //如果没买
                    if(0 == share) {
                        share = cash / closePoint;
                        cash = 0;

                        Trade trade = new Trade();
                        trade.setBuyClosePoint(indexData.getClosePoint());
                        trade.setBuyDate(indexData.getDate());
                        trade.setSellClosePoint(0);
                        trade.setSellDate("a/n");
                        trades.add(trade);
                    }
                }
                //sell 低于了卖点
                else if(decrease_rate<sellRate ) {
                    //如果没卖
                    if(0!= share){
                        cash = closePoint * share * (1-serviceCharge);
                        share = 0;

                        Trade trade = trades.get(trades.size()-1);
                        trade.setSellDate(indexData.getDate());
                        trade.setSellClosePoint(indexData.getClosePoint());
                        float rate = cash / initCash;
                        trade.setRate(rate);

                        if(trade.getSellClosePoint()-trade.getBuyClosePoint()>0) {
                            totalWinRate +=(trade.getSellClosePoint()-trade.getBuyClosePoint())/trade.getBuyClosePoint();
                            winCount++;
                        }

                        else {
                            totalLossRate +=(trade.getSellClosePoint()-trade.getBuyClosePoint())/trade.getBuyClosePoint();
                            lossCount ++;
                        }
                    }
                }
                //do nothing
                else{

                }
            }

            if(share!=0) {
                value = closePoint * share;
            }
            else {
                value = cash;
            }
            float rate = value/initCash;

            Profit profit = new Profit();
            profit.setDate(indexData.getDate());
            profit.setValue(rate*init);

            //System.out.println("profit.value:" + profit.getValue());
            profits.add(profit);
            //System.out.println(initCash);

        }
        avgWinRate = totalWinRate / winCount;
        avgLossRate = totalLossRate / lossCount;

        Map<String,Object> map = new HashMap<>();
        map.put("profits", profits);
        map.put("trades", trades);

        map.put("winCount", winCount);
        map.put("lossCount", lossCount);
        map.put("avgWinRate", avgWinRate);
        map.put("avgLossRate", avgLossRate);
        List<AnnualProfit> annualProfits = caculateAnnualProfits(indexDatas, profits);
        map.put("annualProfits", annualProfits);
        return map;
    }

    private static float getMax(int i, int day, List<IndexData> list) {
        int start = i-1-day;
        if(start<0)
            start = 0;
        int now = i-1;

        if(start<0)
            return 0;

        float max = 0;
        for (int j = start; j < now; j++) {
            IndexData bean =list.get(j);
            if(bean.getClosePoint()>max) {
                max = bean.getClosePoint();
            }
        }
        return max;
    }

    private static float getMA(int i, int ma, List<IndexData> list) {
        int start = i-1-ma;
        int now = i-1;

        if(start<0)
            return 0;

        float sum = 0;
        float avg = 0;
        for (int j = start; j < now; j++) {
            IndexData bean =list.get(j);
            sum += bean.getClosePoint();
        }
        avg = sum / (now - start);
        return avg;
    }

    public float getYear(List<IndexData> allIndexDatas) {
        float years;
        String sDateStart = allIndexDatas.get(0).getDate();
        String sDateEnd = allIndexDatas.get(allIndexDatas.size()-1).getDate();

        Date dateStart = DateUtil.parse(sDateStart);
        Date dateEnd = DateUtil.parse(sDateEnd);

        long days = DateUtil.between(dateStart, dateEnd, DateUnit.DAY);
        years = days/365f;
        return years;
    }

    private List<AnnualProfit> caculateAnnualProfits(List<IndexData> indexDatas, List<Profit> profits) {
        List<AnnualProfit> result = new ArrayList<>();
        String strStartDate = indexDatas.get(0).getDate();
        String strEndDate = indexDatas.get(indexDatas.size()-1).getDate();

        Date startDate = DateUtil.parse(strStartDate);
        Date endDate = DateUtil.parse(strEndDate);

        int startYear = DateUtil.year(startDate);
        int endYear = DateUtil.year(endDate);

        for (int year =startYear; year <= endYear; year++) {
            AnnualProfit annualProfit = new AnnualProfit();
            annualProfit.setYear(year);

            float indexIncome = getIndexIncome(year,indexDatas);
            float trendIncome = getTrendIncome(year,profits);
            annualProfit.setIndexIncome(indexIncome);
            annualProfit.setTrendIncome(trendIncome);
            result.add(annualProfit);

        }
        return result;
    }
    private float getIndexIncome(int year, List<IndexData> indexDatas) {
        IndexData first=null;
        IndexData last=null;

        for (IndexData indexData : indexDatas) {
            String strDate = indexData.getDate();
//          Date date = DateUtil.parse(strDate);
            int currentYear = getYear(strDate);

            if(currentYear == year) {
                if(null==first)
                    first = indexData;
                last = indexData;
            }
        }
        return (last.getClosePoint() - first.getClosePoint()) / first.getClosePoint();
    }
    private float getTrendIncome(int year, List<Profit> profits) {
        Profit first=null;
        Profit last=null;

        for (Profit profit : profits) {
            String strDate = profit.getDate();
            int currentYear = getYear(strDate);

            if(currentYear == year) {
                if(null==first)
                    first = profit;
                last = profit;
            }
            if(currentYear > year)
                break;
        }
        return (last.getValue() - first.getValue()) / first.getValue();
    }
    private int getYear(String date) {
        String strYear= StrUtil.subBefore(date, "-", false);
        return Convert.toInt(strYear);
    }

}
