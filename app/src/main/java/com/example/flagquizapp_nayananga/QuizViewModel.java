package com.example.flagquizapp_nayananga;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

class QuizViewModel extends ViewModel {
    private static final String TAG = "FlagQuiz Activity";
    private static final int FLAGS_IN_QUIZ = 10;

    private HashMap<String,String> fileNameList;
    private List<String> filePathsList;
    private List<String> countryNameList;
    private List<String> correctAnswerList;
    private String correctAnswer;
    private int score;
    private int totalGuesses;
    private int correctAnswers;
    private int guessRows;
    private String s = "{\n" +
            "    \"AD\": \"Andorra\",\n" +
            "    \"AE\": \"United Arab Emirates\",\n" +
            "    \"AF\": \"Afghanistan\",\n" +
            "    \"AG\": \"Antigua and Barbuda\",\n" +
            "    \"AI\": \"Anguilla\",\n" +
            "    \"AL\": \"Albania\",\n" +
            "    \"AM\": \"Armenia\",\n" +
            "    \"AN\": \"Netherlands Antilles\",\n" +
            "    \"AO\": \"Angola\",\n" +
            "    \"AQ\": \"Antarctica\",\n" +
            "    \"AR\": \"Argentina\",\n" +
            "    \"AS\": \"American Samoa\",\n" +
            "    \"AT\": \"Austria\",\n" +
            "    \"AU\": \"Australia\",\n" +
            "    \"AW\": \"Aruba\",\n" +
            "    \"AX\": \"\\u00c5land Islands\",\n" +
            "    \"AZ\": \"Azerbaijan\",\n" +
            "    \"BA\": \"Bosnia and Herzegovina\",\n" +
            "    \"BB\": \"Barbados\",\n" +
            "    \"BD\": \"Bangladesh\",\n" +
            "    \"BE\": \"Belgium\",\n" +
            "    \"BF\": \"Burkina Faso\",\n" +
            "    \"BG\": \"Bulgaria\",\n" +
            "    \"BH\": \"Bahrain\",\n" +
            "    \"BI\": \"Burundi\",\n" +
            "    \"BJ\": \"Benin\",\n" +
            "    \"BL\": \"Saint Barthélemy\",\n" +
            "    \"BM\": \"Bermuda\",\n" +
            "    \"BN\": \"Brunei Darussalam\",\n" +
            "    \"BO\": \"Bolivia, Plurinational State of\",\n" +
            "    \"BQ\": \"Caribbean Netherlands\",\n" +
            "    \"BR\": \"Brazil\",\n" +
            "    \"BS\": \"Bahamas\",\n" +
            "    \"BT\": \"Bhutan\",\n" +
            "    \"BV\": \"Bouvet Island\",\n" +
            "    \"BW\": \"Botswana\",\n" +
            "    \"BY\": \"Belarus\",\n" +
            "    \"BZ\": \"Belize\",\n" +
            "    \"CA\": \"Canada\",\n" +
            "    \"CC\": \"Cocos (Keeling) Islands\",\n" +
            "    \"CD\": \"Congo, the Democratic Republic of the\",\n" +
            "    \"CF\": \"Central African Republic\",\n" +
            "    \"CG\": \"Congo\",\n" +
            "    \"CH\": \"Switzerland\",\n" +
            "    \"CI\": \"C\\u00f4te d'Ivoire\",\n" +
            "    \"CK\": \"Cook Islands\",\n" +
            "    \"CL\": \"Chile\",\n" +
            "    \"CM\": \"Cameroon\",\n" +
            "    \"CN\": \"China\",\n" +
            "    \"CO\": \"Colombia\",\n" +
            "    \"CR\": \"Costa Rica\",\n" +
            "    \"CU\": \"Cuba\",\n" +
            "    \"CV\": \"Cape Verde\",\n" +
            "    \"CW\": \"Cura\\u00e7ao\",\n" +
            "    \"CX\": \"Christmas Island\",\n" +
            "    \"CY\": \"Cyprus\",\n" +
            "    \"CZ\": \"Czech Republic\",\n" +
            "    \"DE\": \"Germany\",\n" +
            "    \"DJ\": \"Djibouti\",\n" +
            "    \"DK\": \"Denmark\",\n" +
            "    \"DM\": \"Dominica\",\n" +
            "    \"DO\": \"Dominican Republic\",\n" +
            "    \"DZ\": \"Algeria\",\n" +
            "    \"EC\": \"Ecuador\",\n" +
            "    \"EE\": \"Estonia\",\n" +
            "    \"EG\": \"Egypt\",\n" +
            "    \"EH\": \"Western Sahara\",\n" +
            "    \"ER\": \"Eritrea\",\n" +
            "    \"ES\": \"Spain\",\n" +
            "    \"ET\": \"Ethiopia\",\n" +
            "    \"EU\": \"Europe\",\n" +
            "    \"FI\": \"Finland\",\n" +
            "    \"FJ\": \"Fiji\",\n" +
            "    \"FK\": \"Falkland Islands (Malvinas)\",\n" +
            "    \"FM\": \"Micronesia, Federated States of\",\n" +
            "    \"FO\": \"Faroe Islands\",\n" +
            "    \"FR\": \"France\",\n" +
            "    \"GA\": \"Gabon\",\n" +
            "    \"GB-ENG\": \"England\",\n" +
            "    \"GB-NIR\": \"Northern Ireland\",\n" +
            "    \"GB-SCT\": \"Scotland\",\n" +
            "    \"GB-WLS\": \"Wales\",\n" +
            "    \"GB\": \"United Kingdom\",\n" +
            "    \"GD\": \"Grenada\",\n" +
            "    \"GE\": \"Georgia\",\n" +
            "    \"GF\": \"French Guiana\",\n" +
            "    \"GG\": \"Guernsey\",\n" +
            "    \"GH\": \"Ghana\",\n" +
            "    \"GI\": \"Gibraltar\",\n" +
            "    \"GL\": \"Greenland\",\n" +
            "    \"GM\": \"Gambia\",\n" +
            "    \"GN\": \"Guinea\",\n" +
            "    \"GP\": \"Guadeloupe\",\n" +
            "    \"GQ\": \"Equatorial Guinea\",\n" +
            "    \"GR\": \"Greece\",\n" +
            "    \"GS\": \"South Georgia and the South Sandwich Islands\",\n" +
            "    \"GT\": \"Guatemala\",\n" +
            "    \"GU\": \"Guam\",\n" +
            "    \"GW\": \"Guinea-Bissau\",\n" +
            "    \"GY\": \"Guyana\",\n" +
            "    \"HK\": \"Hong Kong\",\n" +
            "    \"HM\": \"Heard Island and McDonald Islands\",\n" +
            "    \"HN\": \"Honduras\",\n" +
            "    \"HR\": \"Croatia\",\n" +
            "    \"HT\": \"Haiti\",\n" +
            "    \"HU\": \"Hungary\",\n" +
            "    \"ID\": \"Indonesia\",\n" +
            "    \"IE\": \"Ireland\",\n" +
            "    \"IL\": \"Israel\",\n" +
            "    \"IM\": \"Isle of Man\",\n" +
            "    \"IN\": \"India\",\n" +
            "    \"IO\": \"British Indian Ocean Territory\",\n" +
            "    \"IQ\": \"Iraq\",\n" +
            "    \"IR\": \"Iran, Islamic Republic of\",\n" +
            "    \"IS\": \"Iceland\",\n" +
            "    \"IT\": \"Italy\",\n" +
            "    \"JE\": \"Jersey\",\n" +
            "    \"JM\": \"Jamaica\",\n" +
            "    \"JO\": \"Jordan\",\n" +
            "    \"JP\": \"Japan\",\n" +
            "    \"KE\": \"Kenya\",\n" +
            "    \"KG\": \"Kyrgyzstan\",\n" +
            "    \"KH\": \"Cambodia\",\n" +
            "    \"KI\": \"Kiribati\",\n" +
            "    \"KM\": \"Comoros\",\n" +
            "    \"KN\": \"Saint Kitts and Nevis\",\n" +
            "    \"KP\": \"Korea, Democratic People's Republic of\",\n" +
            "    \"KR\": \"Korea, Republic of\",\n" +
            "    \"KW\": \"Kuwait\",\n" +
            "    \"KY\": \"Cayman Islands\",\n" +
            "    \"KZ\": \"Kazakhstan\",\n" +
            "    \"LA\": \"Lao People's Democratic Republic\",\n" +
            "    \"LB\": \"Lebanon\",\n" +
            "    \"LC\": \"Saint Lucia\",\n" +
            "    \"LI\": \"Liechtenstein\",\n" +
            "    \"LK\": \"Sri Lanka\",\n" +
            "    \"LR\": \"Liberia\",\n" +
            "    \"LS\": \"Lesotho\",\n" +
            "    \"LT\": \"Lithuania\",\n" +
            "    \"LU\": \"Luxembourg\",\n" +
            "    \"LV\": \"Latvia\",\n" +
            "    \"LY\": \"Libya\",\n" +
            "    \"MA\": \"Morocco\",\n" +
            "    \"MC\": \"Monaco\",\n" +
            "    \"MD\": \"Moldova, Republic of\",\n" +
            "    \"ME\": \"Montenegro\",\n" +
            "    \"MF\": \"Saint Martin\",\n" +
            "    \"MG\": \"Madagascar\",\n" +
            "    \"MH\": \"Marshall Islands\",\n" +
            "    \"MK\": \"Macedonia, the former Yugoslav Republic of\",\n" +
            "    \"ML\": \"Mali\",\n" +
            "    \"MM\": \"Myanmar\",\n" +
            "    \"MN\": \"Mongolia\",\n" +
            "    \"MO\": \"Macao\",\n" +
            "    \"MP\": \"Northern Mariana Islands\",\n" +
            "    \"MQ\": \"Martinique\",\n" +
            "    \"MR\": \"Mauritania\",\n" +
            "    \"MS\": \"Montserrat\",\n" +
            "    \"MT\": \"Malta\",\n" +
            "    \"MU\": \"Mauritius\",\n" +
            "    \"MV\": \"Maldives\",\n" +
            "    \"MW\": \"Malawi\",\n" +
            "    \"MX\": \"Mexico\",\n" +
            "    \"MY\": \"Malaysia\",\n" +
            "    \"MZ\": \"Mozambique\",\n" +
            "    \"NA\": \"Namibia\",\n" +
            "    \"NC\": \"New Caledonia\",\n" +
            "    \"NE\": \"Niger\",\n" +
            "    \"NF\": \"Norfolk Island\",\n" +
            "    \"NG\": \"Nigeria\",\n" +
            "    \"NI\": \"Nicaragua\",\n" +
            "    \"NL\": \"Netherlands\",\n" +
            "    \"NO\": \"Norway\",\n" +
            "    \"NP\": \"Nepal\",\n" +
            "    \"NR\": \"Nauru\",\n" +
            "    \"NU\": \"Niue\",\n" +
            "    \"NZ\": \"New Zealand\",\n" +
            "    \"OM\": \"Oman\",\n" +
            "    \"PA\": \"Panama\",\n" +
            "    \"PE\": \"Peru\",\n" +
            "    \"PF\": \"French Polynesia\",\n" +
            "    \"PG\": \"Papua New Guinea\",\n" +
            "    \"PH\": \"Philippines\",\n" +
            "    \"PK\": \"Pakistan\",\n" +
            "    \"PL\": \"Poland\",\n" +
            "    \"PM\": \"Saint Pierre and Miquelon\",\n" +
            "    \"PN\": \"Pitcairn\",\n" +
            "    \"PR\": \"Puerto Rico\",\n" +
            "    \"PS\": \"Palestine\",\n" +
            "    \"PT\": \"Portugal\",\n" +
            "    \"PW\": \"Palau\",\n" +
            "    \"PY\": \"Paraguay\",\n" +
            "    \"QA\": \"Qatar\",\n" +
            "    \"RE\": \"Réunion\",\n" +
            "    \"RO\": \"Romania\",\n" +
            "    \"RS\": \"Serbia\",\n" +
            "    \"RU\": \"Russian Federation\",\n" +
            "    \"RW\": \"Rwanda\",\n" +
            "    \"SA\": \"Saudi Arabia\",\n" +
            "    \"SB\": \"Solomon Islands\",\n" +
            "    \"SC\": \"Seychelles\",\n" +
            "    \"SD\": \"Sudan\",\n" +
            "    \"SE\": \"Sweden\",\n" +
            "    \"SG\": \"Singapore\",\n" +
            "    \"SH\": \"Saint Helena, Ascension and Tristan da Cunha\",\n" +
            "    \"SI\": \"Slovenia\",\n" +
            "    \"SJ\": \"Svalbard and Jan Mayen Islands\",\n" +
            "    \"SK\": \"Slovakia\",\n" +
            "    \"SL\": \"Sierra Leone\",\n" +
            "    \"SM\": \"San Marino\",\n" +
            "    \"SN\": \"Senegal\",\n" +
            "    \"SO\": \"Somalia\",\n" +
            "    \"SR\": \"Suriname\",\n" +
            "    \"SS\": \"South Sudan\",\n" +
            "    \"ST\": \"Sao Tome and Principe\",\n" +
            "    \"SV\": \"El Salvador\",\n" +
            "    \"SX\": \"Sint Maarten (Dutch part)\",\n" +
            "    \"SY\": \"Syrian Arab Republic\",\n" +
            "    \"SZ\": \"Swaziland\",\n" +
            "    \"TC\": \"Turks and Caicos Islands\",\n" +
            "    \"TD\": \"Chad\",\n" +
            "    \"TF\": \"French Southern Territories\",\n" +
            "    \"TG\": \"Togo\",\n" +
            "    \"TH\": \"Thailand\",\n" +
            "    \"TJ\": \"Tajikistan\",\n" +
            "    \"TK\": \"Tokelau\",\n" +
            "    \"TL\": \"Timor-Leste\",\n" +
            "    \"TM\": \"Turkmenistan\",\n" +
            "    \"TN\": \"Tunisia\",\n" +
            "    \"TO\": \"Tonga\",\n" +
            "    \"TR\": \"Turkey\",\n" +
            "    \"TT\": \"Trinidad and Tobago\",\n" +
            "    \"TV\": \"Tuvalu\",\n" +
            "    \"TW\": \"Taiwan\",\n" +
            "    \"TZ\": \"Tanzania, United Republic of\",\n" +
            "    \"UA\": \"Ukraine\",\n" +
            "    \"UG\": \"Uganda\",\n" +
            "    \"UM\": \"US Minor Outlying Islands\",\n" +
            "    \"US\": \"United States\",\n" +
            "    \"UY\": \"Uruguay\",\n" +
            "    \"UZ\": \"Uzbekistan\",\n" +
            "    \"VA\": \"Holy See (Vatican City State)\",\n" +
            "    \"VC\": \"Saint Vincent and the Grenadines\",\n" +
            "    \"VE\": \"Venezuela, Bolivarian Republic of\",\n" +
            "    \"VG\": \"Virgin Islands, British\",\n" +
            "    \"VI\": \"Virgin Islands, U.S.\",\n" +
            "    \"VN\": \"Viet Nam\",\n" +
            "    \"VU\": \"Vanuatu\",\n" +
            "    \"WF\": \"Wallis and Futuna Islands\",\n" +
            "    \"XK\": \"Kosovo\",\n" +
            "    \"WS\": \"Samoa\",\n" +
            "    \"YE\": \"Yemen\",\n" +
            "    \"YT\": \"Mayotte\",\n" +
            "    \"ZA\": \"South Africa\",\n" +
            "    \"ZM\": \"Zambia\",\n" +
            "    \"ZW\": \"Zimbabwe\"\n" +
            "}";

    public QuizViewModel() {
        fileNameList = new HashMap<>();
        filePathsList = new ArrayList<>();
        countryNameList = new ArrayList<>();
        correctAnswerList = new ArrayList<>();
    }

    public static String getTag() {
        return TAG;
    }

    public static int getFlagsInQuiz() {
        return FLAGS_IN_QUIZ;
    }

    public int getTotalGuesses() {
        return totalGuesses;
    }

    public void setTotalGuesses(int totalGuesses) {
        this.totalGuesses += totalGuesses;
    }

    public void resetTotalGuesses() {
        this.totalGuesses = 0;
    }

    public HashMap<String, String> getFileNameList() {
        return fileNameList;
    }

    public void setFileNameList(AssetManager assets) {
        Object obj;
        JSONObject jsonObject;

        try {
            String[] paths = assets.list("png250px");
            this.filePathsList.addAll(Arrays.asList(paths));

        }catch (IOException e) {
            Log.e(TAG, "Error loading image file names", e);

        }

        obj = JSONValue.parse(s);
        jsonObject = (JSONObject) obj;

        for (Object key : jsonObject.keySet()) {

            Object value = jsonObject.get(key);
            String countryCode = String.valueOf(key);
            countryNameList.add(String.valueOf(value));
            fileNameList.put(countryCode.toLowerCase(),String.valueOf(value));
        }

        Log.v("Countries", String.valueOf(countryNameList.size()));
        Log.v("files", String.valueOf(filePathsList.size()));
        Log.v("filesNames", String.valueOf(fileNameList.toString()));

    }

    public void clearFileNameList(){
        this.fileNameList.clear();
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getCorrectCountryName() {
        return correctAnswer.substring(correctAnswer.indexOf('-') + 1)
                .replace('_', ' ');
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers += correctAnswers;
    }

    public void resetCorrectAnswers() {
        this.correctAnswers = 0;
    }

    public String getNextCountryFlag(){
        int randomNum = ThreadLocalRandom.current().nextInt(0, 255 + 1);
        return filePathsList.get(randomNum);
    }

    public List<String> getCountryNameList() {
        return countryNameList;
    }

    public void setCountryNameList(List<String> contryNameList) {
        this.countryNameList = contryNameList;
    }

    public void clearCountryNameList(){
        this.filePathsList.clear();
    }

    public PopupWindow setPopUpWindowCorrect(View view, Context mContext) {
        mContext.getApplicationContext();
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        return popupWindow;
    }

    public PopupWindow setPopUpWindowWrong(View view, Context mContext) {
        mContext.getApplicationContext();
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window_wrong, null);
        TextView showAnswer = (TextView) popupView
                .findViewById(R.id.textViewAnswer);
        showAnswer.setText(correctAnswer);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        PopupWindow popupWindow = new PopupWindow(popupView, width, height);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        return popupWindow;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void updateScore(int i) {
        score = score + (i);
    }

    public void resetScore() {
        score = 0;
    }

    public List<String> getCorrectAnswerList() {
        return correctAnswerList;
    }

    public void setCorrectAnswerList(String correctAnswer) {
        this.correctAnswerList.add(correctAnswer);
    }

    public void clearCorrectAnswersList(){
        this.correctAnswerList.clear();
    }

    public String getNextCountryFlagRemove() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, filePathsList.size());
        String countryFlag = filePathsList.get(randomNum);
        filePathsList.remove(randomNum);
        return countryFlag;
    }
}

