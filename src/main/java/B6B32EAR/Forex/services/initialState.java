package B6B32EAR.Forex.services;


import B6B32EAR.Forex.jpa.dao.AlgorithmJpaController;
import B6B32EAR.Forex.jpa.dao.CurrencyJpaController;
import B6B32EAR.Forex.jpa.dao.ForexJpaController;
import B6B32EAR.Forex.jpa.entities.Currency;
import B6B32EAR.Forex.jpa.entities.Forex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class initialState {
    @Autowired
    AlgorithmJpaController ajc;

    @Autowired
    CurrencyJpaController cjc;

    @Autowired
    ForexJpaController fjc;



    public initialState(){
        Currency one = new Currency(), two = new Currency();
        one.setName("EUR");
        two.setName("USD");
        Forex forex = new Forex();
        forex.setCurrencyone(one);
        forex.setCurrencytwo(two);
        fjc.persist(forex);
    }
}
