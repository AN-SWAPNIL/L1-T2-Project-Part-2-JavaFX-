package main.transferClass;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CompanyList implements Serializable {
    public List<String> companies;
    public CompanyList(List<String> companies) {
        this.companies = companies;
    }

    public List<String> getCompanies() {
        return companies;
    }

    public void setCompanies(List<String> companies) {
        this.companies = companies;
    }
}
