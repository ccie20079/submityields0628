package com.learning.submityields0628.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.learning.gson.V_GetOddSalsByName_Month;
import com.learning.gson.V_GetPieceworkSalsByName_Month;

import java.util.List;

public class PageViewModel<T> extends ViewModel {

    //定义list
    private  List<V_GetPieceworkSalsByName_Month> v_getPieceworkSalsByName_monthList;       //计件工资
    private List<V_GetOddSalsByName_Month> v_getOddSalsByName_monthList;        //零活工资

    public List<V_GetPieceworkSalsByName_Month> getV_getPieceworkSalsByName_monthList() {
        return v_getPieceworkSalsByName_monthList;
    }

    public void setV_getPieceworkSalsByName_monthList(List<V_GetPieceworkSalsByName_Month> v_getPieceworkSalsByName_monthList) {
        this.v_getPieceworkSalsByName_monthList = v_getPieceworkSalsByName_monthList;
    }

    public List<V_GetOddSalsByName_Month> getV_getOddSalsByName_monthList() {
        return v_getOddSalsByName_monthList;
    }

    public void setV_getOddSalsByName_monthList(List<V_GetOddSalsByName_Month> v_getOddSalsByName_monthList) {
        this.v_getOddSalsByName_monthList = v_getOddSalsByName_monthList;
    }
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });
    /**
     * 相当与setter
     */
    private LiveData<List<?>> listLiveData =Transformations.map(mIndex, new Function<Integer, List<?>>() {
        @Override
        public List<?> apply(Integer input) {
            switch (input){
                case 1:
                    return v_getPieceworkSalsByName_monthList;
                case 2:
                    return getV_getOddSalsByName_monthList();
                default:
                    return null;
            }
        }
    });

    /**
     * getter
     * @return
     */
    public LiveData<List<?>> getListLiveData() {
        return listLiveData;
    }

    public void setListLiveData(LiveData<List<?>> listLiveData) {
        this.listLiveData = listLiveData;
    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

}