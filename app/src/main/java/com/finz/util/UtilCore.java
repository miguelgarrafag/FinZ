package com.finz.util;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.finz.R;
import com.finz.constant.ConstantsCore;
import com.finz.listener.DialogListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class UtilCore {

    private UtilCore() {}

    public static class UtilEfects {

        public static void rotate(View view){
            RotateAnimation animation = new RotateAnimation(0, 360,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);

            animation.setDuration(2000);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setRepeatMode(Animation.REVERSE);
            view.startAnimation(animation);
        }
    }

    public static class UtilRest {

        public static String Utf8Enconde(String val){
            String response = null;
            try {
                response = URLEncoder.encode(val, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    public static class UtilViews{

        public static LinearLayout.LayoutParams getParams(){
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(25, 0, 25, 0);
            return lp;
        }

        public static LinearLayout.LayoutParams getParamsEmail(){
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.weight = 1.0f;
            lp.setMargins(100, 0, 100, 0);
            lp.gravity = Gravity.CENTER;
            return lp;
        }

        public static void showCustomDialog(Context context, int title, View input, DialogListener negativeListener, DialogListener positiveListener){
            LinearLayout container = new LinearLayout(context);
            container.addView(input);
            AlertDialog.Builder b = new AlertDialog.Builder(context, R.style.AlertDialogCustom)
                    .setView(container)
                    .setTitle(context.getString(title))
                    .setCancelable(false)
                    .setNegativeButton(context.getString(R.string.label_cancel), (dialog, which) -> negativeListener.onListener())
                    .setPositiveButton(context.getString(R.string.label_ok),  null);


            AlertDialog dialog = b.create();
            Objects.requireNonNull(dialog.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            dialog.setOnShowListener(dialog1 -> {
                Button button = ((AlertDialog) dialog1).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(v -> {
                    EditText e = (EditText) input;
                    if(e.getText().toString().isEmpty())
                        e.setError(context.getString(R.string.str_error_empty));
                    else {
                        positiveListener.onListener();
                        dialog1.dismiss();
                    }
                });
            });
            dialog.show();
        }

        public static void showCustomDialog(Context context, int title, int msg, View input){
            LinearLayout container = new LinearLayout(context);
            container.addView(input);

            AlertDialog.Builder b = new AlertDialog.Builder(context, R.style.AlertDialogCustom)
                    .setView(container)
                    .setTitle(context.getString(title))
                    .setMessage(context.getString(msg))
                    .setCancelable(true);


            AlertDialog dialog = b.create();

            dialog.show();
        }

        public static void showCustomDialog(Context context, int title, View input, View input2, DialogListener negativeListener, DialogListener positiveListener){
            LinearLayout container = new LinearLayout(context);
            container.setOrientation(LinearLayout.VERTICAL);
            container.addView(input);
            container.addView(input2);
            AlertDialog.Builder b = new AlertDialog.Builder(context, R.style.AlertDialogCustom)
                    .setView(container)
                    .setTitle(context.getString(title))
                    .setCancelable(false)
                    .setNegativeButton(context.getString(R.string.label_cancel), (dialog, which) -> negativeListener.onListener())
                    .setPositiveButton(context.getString(R.string.label_ok),  null);


            AlertDialog dialog = b.create();
            Objects.requireNonNull(dialog.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            dialog.setOnShowListener(dialog1 -> {
                Button button = ((AlertDialog) dialog1).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(v -> {
                    EditText e = (EditText) input;
                    EditText e2 = (EditText) input2;
                    if(e.getText().toString().isEmpty())
                        e.setError(context.getString(R.string.str_error_empty));
                    else if(e2.getText().toString().isEmpty())
                        e2.setError(context.getString(R.string.str_error_empty));
                    else {
                        positiveListener.onListener();
                        dialog1.dismiss();
                    }
                });
            });
            dialog.show();
        }

        public static void showCustomDialog(Context context, int title, int msg, View input, View input2, View input3, DialogListener negativeListener, DialogListener positiveListener){
            LinearLayout container = new LinearLayout(context);
            container.setOrientation(LinearLayout.VERTICAL);
            container.addView(input);
            container.addView(input2);
            container.addView(input3);

            AlertDialog.Builder b = new AlertDialog.Builder(context, R.style.AlertDialogCustom)
                    .setView(container)
                    .setTitle(title)
                    .setMessage(context.getString(msg))
                    .setCancelable(false)
                    .setNegativeButton(context.getString(R.string.label_cancel), (dialog, which) -> negativeListener.onListener())
                    .setPositiveButton(context.getString(R.string.label_ok),  null);

            AlertDialog dialog = b.create();
            Objects.requireNonNull(dialog.getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

            dialog.setOnShowListener(dialog1 -> {
                Button button = ((AlertDialog) dialog1).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(v -> {
                    EditText e = (EditText) input;
                    EditText e2 = (EditText) input2;
                    EditText e3 = (EditText) input3;
                    if(e.getText().toString().isEmpty())
                        e.setError(context.getString(R.string.str_error_empty));
                    else if(e2.getText().toString().isEmpty())
                        e2.setError(context.getString(R.string.str_error_empty));
                    else if(e3.getText().toString().isEmpty())
                        e3.setError(context.getString(R.string.str_error_empty));
                    else {
                        positiveListener.onListener();
                        dialog1.dismiss();
                    }
                });
            });
            dialog.show();
        }

        public static void showCustomDialog(Context context, int title, String msg){
            new AlertDialog.Builder(context, R.style.AlertDialogCustom)
                    .setTitle(context.getString(title))
                    .setMessage(msg)
                    .show();
        }

        public static void showCustomDialog(Context context, int title, String msg, DialogListener negativeListener, DialogListener positiveListener){
            new AlertDialog.Builder(context, R.style.AlertDialogCustom)
                    .setTitle(context.getString(title))
                    .setMessage(msg)
                    .setCancelable(false)
                    .setNegativeButton(context.getString(R.string.label_cancel), (dialog, which) -> negativeListener.onListener())
                    .setPositiveButton(context.getString(R.string.label_ok),  (dialog, which) -> positiveListener.onListener())
                    .show();
        }
    }

    public static class UtilNetwork {
        public static boolean isNetworkAvailable(Context context) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

            if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
                return true;
            }else {
                return false;
            }
        }
    }

    public static class UtilDate {

        @SuppressLint("SimpleDateFormat")
        public static SimpleDateFormat dateFormatService = new SimpleDateFormat("dd/MM/yyyy");

        public static void showDateDialog(Context ctx, TextInputEditText edit, TextInputEditText next) {
            new DatePickerDialog(Objects.requireNonNull(ctx), (view, year, month, dayOfMonth) -> {
                final int mesActual = month + 1;
                String day = (dayOfMonth < 10)? ConstantsCore.Utilities.ZERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String monthS = (mesActual < 10)? ConstantsCore.Utilities.ZERO + String.valueOf(mesActual):String.valueOf(mesActual);
                edit.setText(ctx.getString(R.string.blank_date, day, monthS, year));
                next.requestFocus();
            },Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show();
        }

        public static String formatDateNoYear(Context context, Date date) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            String day = getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
            String month = getMonth(c.get(Calendar.MONTH));
            int dayNumber = c.get(Calendar.DAY_OF_MONTH);

            c.setTime(new Date());
            if(day.equals(getDayOfWeek(c.get(Calendar.DAY_OF_WEEK))) && month.equals(getMonth(c.get(Calendar.MONTH))))
                return context.getString(R.string.label_today) + " " +  day + " " + dayNumber + " " + context.getString(R.string.label_of) + " " + month;
            else
                return day + " " + dayNumber + " " + context.getString(R.string.label_of) + " " + month;
        }

        public static String formatDateYear(Context context, Date date) {
            Calendar c = Calendar.getInstance();
            c.setTime(date);

            String day = getDayOfWeek(c.get(Calendar.DAY_OF_WEEK));
            String month = getMonth(c.get(Calendar.MONTH));
            int dayNumber = c.get(Calendar.DAY_OF_MONTH);
            int year = c.get(Calendar.YEAR);

            return day + " " + dayNumber + " " + context.getString(R.string.label_of) + " " + month + " " + year;
        }

        private static String getDayOfWeek(int day) {
            String text = "";
            switch (day) {
                case 1:
                    text = "Domingo";
                    break;
                case 2:
                    text = "Lunes";
                    break;
                case 3:
                    text = "Martes";
                    break;
                case 4:
                    text = "Miércoles";
                    break;
                case 5:
                    text = "Jueves";
                    break;
                case 6:
                    text = "Viernes";
                    break;
                case 7:
                    text = "Sábado";
                    break;
            }
            return text;
        }

        private static String getMonth(int month) {
            String text = "";
            switch (month) {
                case 0:
                    text = "Enero";
                    break;
                case 1:
                    text = "Febrero";
                    break;
                case 2:
                    text = "Marzo";
                    break;
                case 3:
                    text = "Abril";
                    break;
                case 4:
                    text = "Mayo";
                    break;
                case 5:
                    text = "Junio";
                    break;
                case 6:
                    text = "Julio";
                    break;
                case 7:
                    text = "Agosto";
                    break;
                case 8:
                    text = "Setiembre";
                    break;
                case 9:
                    text = "Octubre";
                    break;
                case 10:
                    text = "Noviembre";
                    break;
                case 11:
                    text = "Diciembre";
                    break;
            }
            return text;
        }

    }

    public static class UtilUI {

        private UtilUI() {}

        public static ProgressDialog showProgressDialog(Context context) {
            return showProgressDialogMain(context,context.getResources().getString(R.string.str_loading));
        }

        public static ProgressDialog showProgressDialog(Context context, String message) {
            return showProgressDialogMain(context,message);
        }

        private static ProgressDialog showProgressDialogMain(Context context, String message){
            ProgressDialog dialog = new ProgressDialog(context);
            dialog.setMessage(message);
            dialog.setCancelable(false);
            dialog.show();

            return dialog;
        }
    }
}
