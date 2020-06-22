package utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBUtil {
    //変数の宣言
    private static final String PERSISTENCE_UNIT_NAME = "daily_report_system";
    private static EntityManagerFactory emf;
    
    //createEntityManagerメソッドの宣言
    //_getEntityManagerFactoryメソッド返り値のインスタンスから
    //更にcreateEntityManagerメソッドを呼び出して結果を返してる
    public static EntityManager createEntityManager() {
        return _getEntityManagerFactory().createEntityManager();
    }

    private static EntityManagerFactory _getEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        }
        return emf;
    }
}
