package com.vinu.games;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuessWhoActivity extends AppCompatActivity {

    ImageView imageView;
    Button option1;
    Button option2;
    Button option3;
    Button option4;
    ArrayList<String> celebURLs = new ArrayList<>();
    ArrayList<String> celebNames = new ArrayList<>();
    int chosenCeleb = 0;
    int locationOfCorrectAnswer = 0;
    String[] answers = new String[4];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_who);
        imageView = findViewById(R.id.imageView);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);

        fetchCelebs();
        save();
        createQuestion();
    }

    private void save(){
        ImageDownloader imageDownloader = new ImageDownloader();
        String path = "D:/workspace/android/Games/app/src/main/res/raw/images";

        try {
            for(int i=0; i< celebURLs.size(); i++){
                Bitmap image = imageDownloader.execute(celebURLs.get(i)).get();
                File file = new File(new File(path), "amir.jpg");
                //file.createNewFile();
                FileOutputStream out = new FileOutputStream(file);
                image.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void fetchCelebs() {
        HTMLParser parser = new HTMLParser();
        try {
            String result = parser.execute("https://www.imdb.com/list/ls020280202/").get();
            if (result != null && !result.isEmpty()) {
                String requiredSource = result.split("class=\"lister-list\"")[1]
                                                .split("class=\"footer filmosearch\"")[0];

                Pattern p = Pattern.compile("img alt=\"(.*?)\"");
                Matcher m = p.matcher(requiredSource);
                while (m.find()) {
                    celebNames.add(m.group(1));
                }
                p = Pattern.compile("src=\"(.*?)\"");
                m = p.matcher(requiredSource);
                while (m.find()) {
                    celebURLs.add(m.group(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createQuestion() {
        Random random = new Random();
        chosenCeleb = random.nextInt(celebNames.size());

        ImageDownloader imageDownloader = new ImageDownloader();
        Bitmap image;

        try{
            image = imageDownloader.execute(celebURLs.get(chosenCeleb)).get();

            imageView.setImageBitmap(image);
            locationOfCorrectAnswer = random.nextInt(4);
            int incorrectLocation;
            for(int i=0; i<4; i++){
                if(i == locationOfCorrectAnswer){
                    answers[i] = celebNames.get(chosenCeleb);
                }else{
                    incorrectLocation = random.nextInt(celebNames.size());
                    while(incorrectLocation == chosenCeleb){
                        incorrectLocation = random.nextInt(celebNames.size());
                    }
                    answers[i] = celebNames.get(incorrectLocation);
                }
            }
            option1.setText(answers[0]);
            option2.setText(answers[1]);
            option3.setText(answers[2]);
            option4.setText(answers[3]);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void chooseOption(View view){
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            Toast.makeText(getApplicationContext(), "Correct!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "Wrong! It was " + celebNames.get(chosenCeleb), Toast.LENGTH_SHORT).show();
        }
        createQuestion();
    }

    public void backToHome(View view){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
