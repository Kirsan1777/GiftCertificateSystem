package com.epam.esm.controller;

import com.epam.esm.dao.impl.LinkTableDAOImpl;
import com.epam.esm.model.GiftTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.util.List;


/**
 * Class of concatenated tables controller to handle requests and response
 */
@RestController
@RequestMapping("many")
@EnableAutoConfiguration
public class GiftTagController {

    @Autowired
    private final LinkTableDAOImpl linkTableDAO;

    public GiftTagController(LinkTableDAOImpl linkTableDAO) {
        this.linkTableDAO = linkTableDAO;
    }

    @GetMapping("/concatenated-tables")
    public List<GiftTag> concatenatedTables() {
        return linkTableDAO.getConcatenatedTables("");
    }

    @GetMapping("/sortDateAsc")
    public List<GiftTag> sortByDateASC() {
        return linkTableDAO.getConcatenatedTables(" ORDER BY c.name ASC");
    }

    @GetMapping("/sortDateDesc")
    public List<GiftTag> sortByDateDESC() {
        return linkTableDAO.getConcatenatedTables(" ORDER BY c.name DESC");
    }

    @GetMapping("/sortTagNameAsc")
    public List<GiftTag> sortByTagNameASC() {
        return linkTableDAO.getConcatenatedTables(" ORDER BY t.name ASC");
    }

    @GetMapping("/sortTagNameDesc")
    public List<GiftTag> sortByTagNameDESC() {
        return linkTableDAO.getConcatenatedTables(" ORDER BY t.name DESC");
    }

    @GetMapping("/findByAllParam")
    public List<GiftTag> sortByAllParam(@RequestParam(value = "nameTag") String nameTag,
                                        @RequestParam(value = "nameGift") String nameGift,
                                        @RequestParam(value = "typeSort") String typeSort,
                                        @RequestParam(value = "orderBy") String orderBy,
                                        @RequestParam(value = "chooseType") String chooseType) {

        StringBuffer stringBuffer = new StringBuffer();
        int count = 0;
        if (nameTag != null && !nameTag.equals("")) {
            stringBuffer.append(" WHERE t.name = '").append(nameTag).append("'");
            count++;
        }
        if (nameGift != null && chooseType != null && !nameGift.equals("") && !chooseType.equals("")) {
            if (count > 0) {
                stringBuffer.append(" AND ");
            } else {
                stringBuffer.append(" WHERE ");
            }
            stringBuffer.append(chooseType).append(" LIKE '%").append(nameGift).append("%'");
            count++;
        }
        if (!orderBy.equals("")) {
            stringBuffer.append(" ORDER BY ").append(orderBy).append(" ").append(typeSort);
        }
        return linkTableDAO.getConcatenatedTables(stringBuffer.toString());
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
                "Mistake gift tag controller \nexception : " + ex.getMessage() + "\n" + HttpStatus.FORBIDDEN, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

}
