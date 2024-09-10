package com.rpsa.rpsa.controller;

import com.rpsa.rpsa.model.M_Holidays;
import com.rpsa.rpsa.model.T_Appeals;
import com.rpsa.rpsa.repository.M_HolidaysRepository;
import com.rpsa.rpsa.repository.TAppealsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@EnableScheduling
@Controller
public class SlaScheduler {

    @Autowired
    private TAppealsRepository appealsRepo;

    @Autowired
    private M_HolidaysRepository holidaysRepo;


    @Scheduled(cron = "0 0 10 * * ?")
//    @Scheduled(cron = "*/20 * * * * ?")
    public void calculateSLA() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
//        System.out.println(cal.get(Calendar.DAY_OF_WEEK));
//        if (cal.get(Calendar.DAY_OF_WEEK) != 7 && cal.get(Calendar.DAY_OF_WEEK) != 1) {
//            M_Holidays h = asdao.getHoliday(new Date());
//            if (h == null) {
        List<T_Appeals> appeals = appealsRepo.findAll();
        for (T_Appeals t : appeals) {
//                    if (t.getAppeallevel().equals("1") && t.getPaymentflag().equals("Y") && !t.getStatusid().getStatusid().equals("3") && !t.getStatusid().getStatusid().equals("5")) {
            if (t.getAppeallevel().equals("1") && !t.getStatusid().getStatusid().equals("3") && !t.getStatusid().getStatusid().equals("5")) {
                Date submissiondate = t.getFirstappealdate();

                Date currentDate = new Date(); // Get the current date
                Calendar calendar = Calendar.getInstance();
                int daysleft = 30;

                System.out.println("submissiondate " + submissiondate);
                System.out.println("daysleft " + daysleft);
                System.out.println("calendar " + calendar);
                while (submissiondate.before(currentDate) || submissiondate.equals(currentDate)) {
                    calendar.setTime(submissiondate);

                    int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                    System.out.println(" dayOfWeek " + dayOfWeek);
                    System.out.println(" Calendar.SATURDAY " + Calendar.SATURDAY);
                    // Check if it's Saturday (Calendar.SATURDAY) or Sunday (Calendar.SUNDAY)
                    if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                        System.out.println("Weekend date: " + submissiondate);
                    } else {
                        M_Holidays h = holidaysRepo.findById(submissiondate).orElse(null);

                        if (h == null) {
                            daysleft--;
                        }

                    }

                    // Increment 'submissiondate' by one day (you can adjust the time unit as needed)
                    calendar.add(Calendar.DAY_OF_MONTH, 1);
                    submissiondate = calendar.getTime();

                }
                t.setDaysleft(String.valueOf(daysleft));
                appealsRepo.save(t);

//                        Date todate = new Date();
//                        long diff = todate.getTime() - t.getFirstappealdate().getTime();
////                        System.out.println("appealcode " + t.getAppealcode() + " " + (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
//                        int daysleft = 30 - (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
//                        t.setDaysleft(String.valueOf(daysleft));
//                        String res = asdao.saveappeals(t);
            }
            if (t.getAppeallevel().equals("2") && (t.getStatusid().getStatusid().equals("7") || t.getStatusid().getStatusid().equals("6")) && t.getDoaction() == null) {
                if (t.getCompletiondate() != null) {
                    Date submissiondate = t.getCompletiondate();

                    Date currentDate = new Date(); // Get the current date
                    Calendar calendar = Calendar.getInstance();
                    int daysleft = Integer.parseInt(t.getExtrasla());
//            calendar.add(Calendar.DAY_OF_MONTH, 1);
//            submissiondate = calendar.getTime();
                    System.out.println("submissiondate " + submissiondate);
                    System.out.println("daysleft " + daysleft);
                    System.out.println("calendar " + calendar);
                    while (submissiondate.before(currentDate) || submissiondate.equals(currentDate)) {
                        calendar.setTime(submissiondate);

                        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
                        System.out.println(" dayOfWeek " + dayOfWeek);
                        System.out.println(" Calendar.SATURDAY " + Calendar.SATURDAY);
                        // Check if it's Saturday (Calendar.SATURDAY) or Sunday (Calendar.SUNDAY)
                        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                            System.out.println("Weekend date: " + submissiondate);
                        } else {
                            M_Holidays h = holidaysRepo.findById(submissiondate).orElse(null);

                            if (h == null) {
                                daysleft--;
                            }

                        }

                        // Increment 'submissiondate' by one day (you can adjust the time unit as needed)
                        calendar.add(Calendar.DAY_OF_MONTH, 1);
                        submissiondate = calendar.getTime();

                    }
                    t.setExtrasla(String.valueOf(daysleft));
                    appealsRepo.save(t);

                }

            }
        }
    }
}
