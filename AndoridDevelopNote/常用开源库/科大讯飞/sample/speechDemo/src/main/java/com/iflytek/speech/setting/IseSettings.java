/**
 *
 */
package com.iflytek.speech.setting;

import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Window;
import android.widget.Toast;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.voicedemo.R;

import java.util.HashMap;

/**
 * 评测设置界面
 */
public class IseSettings extends PreferenceActivity {
    private final static String PREFER_NAME = "ise_settings";

    private ListPreference mLanguagePref;
    private ListPreference mCategoryPref;
    private ListPreference mResultLevelPref;
    private EditTextPreference mVadBosPref;
    private EditTextPreference mVadEosPref;
    private EditTextPreference mSpeechTimeoutPref;

    private Toast mToast;

    private HashMap<String, String> zhMap = new HashMap<>();
    private HashMap<String, String> enMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getPreferenceManager().setSharedPreferencesName(PREFER_NAME);
        addPreferencesFromResource(R.xml.ise_settings);
        initCategoryMap();
        initUI();
    }

    private void initCategoryMap() {
        zhMap.put("read_syllable", "单字");
        zhMap.put("read_word", "词语");
        zhMap.put("read_sentence", "句子");
        zhMap.put("read_chapter", "篇章");

        enMap.put("read_word", "词语");
        enMap.put("read_sentence", "句子");
        enMap.put("read_chapter", "篇章");
        enMap.put("simple_expression", "英文情景反应");
        enMap.put("read_choice", "英文选择题");
        enMap.put("topic", "英文自由题");
        enMap.put("retell", "英文复述题");
        enMap.put("picture_talk", "英文看图说话");
        enMap.put("oral_translation", "英文口头翻译");
    }

    private void initUI() {
        mLanguagePref = (ListPreference) findPreference(SpeechConstant.LANGUAGE);
        mCategoryPref = (ListPreference) findPreference(SpeechConstant.ISE_CATEGORY);
        mResultLevelPref = (ListPreference) findPreference(SpeechConstant.RESULT_LEVEL);
        mVadBosPref = (EditTextPreference) findPreference(SpeechConstant.VAD_BOS);
        mVadEosPref = (EditTextPreference) findPreference(SpeechConstant.VAD_EOS);
        mSpeechTimeoutPref = (EditTextPreference) findPreference(SpeechConstant.KEY_SPEECH_TIMEOUT);

        mToast = Toast.makeText(IseSettings.this, "", Toast.LENGTH_LONG);

        mLanguagePref.setSummary("当前：" + mLanguagePref.getEntry());
        mCategoryPref.setSummary("当前：" + mCategoryPref.getEntry());
        mResultLevelPref.setSummary("当前：" + mResultLevelPref.getEntry());
        mVadBosPref.setSummary("当前：" + mVadBosPref.getText() + "ms");
        mVadEosPref.setSummary("当前：" + mVadEosPref.getText() + "ms");

        String speech_timeout = mSpeechTimeoutPref.getText();
        String summary = "当前：" + speech_timeout;
        if (!"-1".equals(speech_timeout)) {
            summary += "ms";
        }
        mSpeechTimeoutPref.setSummary(summary);

        mLanguagePref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {

                String categoryValue = mCategoryPref.getValue();

                if ("zh_cn".equals(newValue.toString())) {
                    // 中文
                    if ("plain".equals(mResultLevelPref.getValue())) {
                        showTip("汉语评测结果格式不支持plain设置");
                        return false;
                    }
                    if (!zhMap.containsKey(categoryValue)) {
                        showTip("中文评测不支持" + enMap.get(categoryValue));
                        return false;
                    }
                } else {
                    // 英文
                    if (!enMap.containsKey(categoryValue)) {
                        showTip("英语评测不支持" + zhMap.get(categoryValue));
                        return false;
                    }
                }

                int newValueIndex = mLanguagePref.findIndexOfValue(newValue.toString());
                String newEntry = (String) mLanguagePref.getEntries()[newValueIndex];
                mLanguagePref.setSummary("当前：" + newEntry);
                return true;
            }
        });

        mCategoryPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                String categoryValue = newValue.toString();

                if ("en_us".equals(mLanguagePref.getValue())) {
                    if (!enMap.containsKey(categoryValue)) {
                        showTip("英语评测不支持" + zhMap.get(categoryValue) + "，请选其他项");
                        return false;
                    }
                }
                if ("zh_cn".equals(mLanguagePref.getValue())) {
                    if (!zhMap.containsKey(categoryValue)) {
                        showTip("中文评测不支持" + enMap.get(categoryValue) + "，请选其他项");
                        return false;
                    }
                }

                int newValueIndex = mCategoryPref.findIndexOfValue(newValue.toString());
                String newEntry = (String) mCategoryPref.getEntries()[newValueIndex];
                mCategoryPref.setSummary("当前：" + newEntry);
                return true;
            }
        });

        mResultLevelPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if ("zh_cn".equals(mLanguagePref.getValue()) && "plain".equals(newValue.toString())) {
                    showTip("汉语评测不支持plain，请选其他项");
                    return false;
                }

                mResultLevelPref.setSummary("当前：" + newValue.toString());
                return true;
            }
        });

        mVadBosPref.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        mVadBosPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int bos;
                try {
                    bos = Integer.parseInt(newValue.toString());
                } catch (Exception e) {
                    showTip("无效输入！");
                    return false;
                }
                if (bos < 0 || bos > 30000) {
                    showTip("取值范围为0~30000");
                    return false;
                }

                mVadBosPref.setSummary("当前：" + bos + "ms");
                return true;
            }
        });

        mVadEosPref.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        mVadEosPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int eos;
                try {
                    eos = Integer.parseInt(newValue.toString());
                } catch (Exception e) {
                    showTip("无效输入！");
                    return false;
                }
                if (eos < 0 || eos > 30000) {
                    showTip("取值范围为0~30000");
                    return false;
                }

                mVadEosPref.setSummary("当前：" + eos + "ms");
                return true;
            }
        });

        mSpeechTimeoutPref.getEditText().setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED | InputType.TYPE_CLASS_NUMBER);
        mSpeechTimeoutPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                int speech_timeout;
                try {
                    speech_timeout = Integer.parseInt(newValue.toString());
                } catch (Exception e) {
                    showTip("无效输入！");
                    return false;
                }

                if (speech_timeout < -1) {
                    showTip("必须大于等于-1");
                    return false;
                }

                if (speech_timeout == -1) {
                    mSpeechTimeoutPref.setSummary("当前：-1");
                } else {
                    mSpeechTimeoutPref.setSummary("当前：" + speech_timeout + "ms");
                }

                return true;
            }
        });
    }

    private void showTip(String str) {
        if (!TextUtils.isEmpty(str)) {
            mToast.cancel();
            mToast.setText(str);
            mToast.show();
        }
    }
}
