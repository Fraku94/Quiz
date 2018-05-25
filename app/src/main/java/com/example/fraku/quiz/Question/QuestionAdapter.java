package com.example.fraku.quiz.Question;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.fraku.quiz.Database.DatabaseHandlerStats;
import com.example.fraku.quiz.Object.QuestionObject;
import com.example.fraku.quiz.R;

import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionViewHolder>{

    private List<QuestionObject> QuestionList;
    private Context context;

    private DatabaseHandlerStats databaseStats;

    //Przypisanie Obiektów do adaptera
    public QuestionAdapter(List<QuestionObject> QuestionList, Context context){
        this.QuestionList = QuestionList;
        this.context = context;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Przypisanie wygladu okna do adaptera
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, null,false);

        //Ustawienie RecycleView
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);

        //Podpienie i zwrocenie wygladu
        QuestionViewHolder rcv = new QuestionViewHolder((layoutView));


        return rcv;
    }

    @Override
    public void onBindViewHolder(final QuestionViewHolder holder, final int position) {


        databaseStats = new DatabaseHandlerStats(context);
        Cursor resResult = databaseStats.getResultStats(QuestionList.get(position).getIdQue());

        if(resResult.moveToPosition(0)) {

            holder.mQuizResult = resResult.getString(0);
            holder.mQuestionNumCurrent = resResult.getInt(1);
            holder.mQuizGoodAnswer = resResult.getInt(2);
            holder.mQuestionNum = Integer.parseInt(QuestionList.get(position).getQuestionNum());

            if (holder.mQuestionNumCurrent < holder.mQuestionNum){

                holder.mResult.setText("Quzi rozwiązany w : " + holder.mQuizResult + "/100%");

            }else {

                holder.mResult.setText("Ostatni wynik : " + holder.mQuizGoodAnswer + "/"
                        + holder.mQuestionNum  + " Procent : " + holder.mQuizResult + "/100%");

            }
        }

        holder.mQuestionId = QuestionList.get(position).getIdQue();
        holder.mCategoryQuestionNumber = QuestionList.get(position).getQuestionNum();
        holder.mTitle.setText(QuestionList.get(position).getTitleQue());



//        Glide.with(context).load(QuestionList.get(position).getImageUrl()).
//                placeholder(R.mipmap.default_profile).fitCenter().into(holder.mImage);

        Glide.with(context)
                .load(QuestionList.get(position).getImageUrl())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>(100,100) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation){

                        holder.mImage.setImageBitmap(resource);
                        //saveImage(resource, QuestionList.get(position).getIdQue());
                    }
                });

    }

    @Override
    public int getItemCount() {
        return this.QuestionList.size();
    }


//    private String saveImage(Bitmap image, String IdQue) {
//        String savedImagePath = null;
//
//        String imageFileName = "JPEG_" + IdQue + ".jpg";
//        File storageDir = new File(            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
//                + "/Quiz");
//        boolean success = true;
//        if (!storageDir.exists()) {
//            success = storageDir.mkdirs();
//        }
//        if (success) {
//            File imageFile = new File(storageDir, imageFileName);
//            savedImagePath = imageFile.getAbsolutePath();
//            try {
//                OutputStream fOut = new FileOutputStream(imageFile);
//                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
//                fOut.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            // Add the image to the system gallery
//            galleryAddPic(savedImagePath);
//            Toast.makeText(context, "IMAGE SAVED", Toast.LENGTH_LONG).show();
//        }
//        return savedImagePath;
//    }
//
//    private void galleryAddPic(String imagePath) {
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(imagePath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        context.sendBroadcast(mediaScanIntent);
//    }
}
