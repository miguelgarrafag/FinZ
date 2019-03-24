package com.finz.util;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.finz.R;
import com.finz.constant.ConstantsCore;
import com.finz.listener.DialogListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

        public static LinearLayout.LayoutParams getParamsBank(){
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(25, 0, 25, 80);
            return lp;
        }

        public static LinearLayout.LayoutParams getParamsEmail(){
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lp.weight = 1.0f;
            lp.setMargins(100, 0, 100, 0);
            lp.gravity = Gravity.CENTER;
            return lp;
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

        public static AlertDialog showCustomDialogControl(Context context, int title, int msg, View input){
            LinearLayout container = new LinearLayout(context);
            container.addView(input);

            AlertDialog.Builder b = new AlertDialog.Builder(context, R.style.AlertDialogCustom)
                    .setView(container)
                    .setTitle(context.getString(title))
                    .setMessage(context.getString(msg))
                    .setCancelable(true);


            AlertDialog dialog = b.create();

            dialog.show();

            return dialog;
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

        @SuppressLint("SimpleDateFormat")
        private static SimpleDateFormat dateFormatServiceNew = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        public static String getDateService(String date) {
            date = date.substring(0,19);
            date = date.replace("T", " ");
            try {
                return dateFormatService.format(dateFormatServiceNew.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

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

        public static void question(Context context, String emailV) {
            final TextView email = new EditText(context);

            email.setLayoutParams(UtilCore.UtilViews.getParamsEmail());
            email.setPadding(0,0,0,0);
            email.setTextColor(ContextCompat.getColor(context, R.color.colorAccent));
            email.setTypeface(ResourcesCompat.getFont(context, R.font.product_sans_regular));
            email.setGravity(Gravity.CENTER);
            email.setFocusable(false);
            email.setTextSize(15);
            email.setBackground(ContextCompat.getDrawable(context, R.drawable.selector_button_lose_pass));
            email.setText(Html.fromHtml(context.getResources().getString(R.string.dialog_help_email, emailV)));

            UtilCore.UtilViews.showCustomDialog(context,
                    R.string.dialog_help,
                    R.string.dialog_help_msg,
                    email);

            email.setOnClickListener(v -> sendEmail(context, emailV));
        }

        private static void sendEmail(Context context, String email) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto",email, null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.str_subject));
            context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
        }
    }

    public static class UtilFunctions {

        private UtilFunctions() {}

        public static List<String> getListRemoteConfig(String list) {
            JSONArray jsonArray = null;
            try {
                jsonArray = new JSONArray(list);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Type listType = new TypeToken<List<String>>() {}.getType();
            return new Gson().fromJson(Objects.requireNonNull(jsonArray).toString(), listType);
        }

    }

    public static class UtilDraw {

        private UtilDraw() {}

        public static File createImageFile(Context context) throws IOException {
            @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String imageFileName = "DNI_" + timeStamp + "_";
            File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            return File.createTempFile(
                    imageFileName,  /* prefix */
                    ".jpg",         /* suffix */
                    storageDir      /* directory */
            );
        }

        public static Bitmap rotateBitmapOrientation(String photoFilePath) {
            BitmapFactory.Options bounds = new BitmapFactory.Options();
            bounds.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(photoFilePath, bounds);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            Bitmap bm = BitmapFactory.decodeFile(photoFilePath, opts);

            ExifInterface exif = null;

            try {exif = new ExifInterface(photoFilePath);}
            catch (IOException e) {e.printStackTrace();}

            String orientString = Objects.requireNonNull(exif).getAttribute(ExifInterface.TAG_ORIENTATION);
            int orientation = orientString != null ? Integer.parseInt(orientString) : ExifInterface.ORIENTATION_NORMAL;
            int rotationAngle = 0;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) rotationAngle = 90;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_180) rotationAngle = 180;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_270) rotationAngle = 270;

            Matrix matrix = new Matrix();
            matrix.setRotate(rotationAngle, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
            return Bitmap.createBitmap(bm, 0, 0, bounds.outWidth, bounds.outHeight, matrix, true);
        }

        public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {
            int width = image.getWidth();
            int height = image.getHeight();

            float bitmapRatio = (float)width / (float) height;
            if (bitmapRatio > 1) {
                width = maxSize;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }
            return Bitmap.createScaledBitmap(image, width, height, true);
        }

    }
}
