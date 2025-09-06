package mayton.chess;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

// http://127.0.0.1:8081/queens/ping
//
// http://127.0.0.1:8081/ens/find/
//
// http://127.0.0.1:8081/greeting?name=Hello
//
@RestController
@RequestMapping("/queens")
public class QueensRestController {

    static Logger logger = LoggerFactory.getLogger(QueensRestController.class);

    @Autowired
    PositionRepository positionRepository;

    public List<String> asList(String s){
        List<String> list = new ArrayList<>();
        list.add(s);
        return list;
    }

    public MultiValueMap<String, String> cacheForeverHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.put("Cache-Control",asList("max-age=31556926"));
        return headers;
    }

    public MultiValueMap<String, String> noCacheHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Cache-Control",asList("no-cache, no-store, max-age=0, must-revalidate"));
        headers.put("Expires",asList("0"));
        headers.put("Pragma",asList("no-cache"));
        return headers;
    };

    @RequestMapping(value = "/ping", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String ping(){
        return "OK!";
    }

    @RequestMapping(value = "/addPlain", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> addPlain(@RequestBody String positionCode) {
        try {
            if (QueenProcessor.checkPosition(positionCode)) {
                Position9 savedPosition9 = positionRepository.save(new Position9(positionCode));
                logger.info("Saved : {}", savedPosition9.toString());
                return new ResponseEntity<>("Saved OK!", HttpStatus.OK);
            } else {
                logger.warn("PositionCode doesn't complete checks!");
                return new ResponseEntity<>("PositionCode doesn't complete checks!", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/addJson", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Status> addJson(@RequestBody String positionCodeJson) {
        try {
            Gson gson = new Gson();
            PositionCodeJson pcj = gson.fromJson(positionCodeJson, PositionCodeJson.class);
            String positionCode = pcj.getPositionCode();
            int size = pcj.getSize();
            if (QueenProcessor.checkPosition(positionCode) && QueenProcessor.checkAllowedDesks(size)){
                Position9 savedPosition9 = positionRepository.save(new Position9(pcj.getPositionCode()));
                logger.info("Saved : {}", savedPosition9.toString());
                return new ResponseEntity<>(new Status("Saved OK!"), HttpStatus.OK);
            } else {
                logger.warn("PositionCode doesn't complete checks!");
                return new ResponseEntity<>(new Status("PositionCode doesn't complete checks!"), HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return new ResponseEntity<>(new Status(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/findJson", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Position9>> findJson(@RequestBody String positionCode) throws IOException {
        if (QueenProcessor.containsMetaSymbols(positionCode)){
            // Non-Cached
        } else {
            //
        }
        return null;
    }

    @RequestMapping(value = "/findPlain", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public void findPlain(@RequestBody String positionCode, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        if (QueenProcessor.containsMetaSymbols(positionCode)){
            // Non-Cached
            List<Position9> position9s = positionRepository.findByPositionCodeLike(positionCode);
            logger.info("Found : {} records by positionCode like '{}'", position9s.size(), positionCode);
            //return new ResponseEntity<>(position9s, noCacheHeaders(), HttpStatus.OK);
            resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
            resp.setStatus(HttpServletResponse.SC_OK);
            for(Position9 position9 : position9s){
                writer.println(position9.getPositionCode());
            }
            resp.flushBuffer();
        } else {
            List<Position9> position9s = positionRepository.findByPositionCode(positionCode);
            logger.info("Found : {} records by positionCode = '{}'", position9s.size(), positionCode);
            // If positive response then Cached
            if (position9s.size() == 0){
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
                //return new ResponseEntity<>(position9s, noCacheHeaders(), HttpStatus.OK);
            } else {
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType(MediaType.TEXT_PLAIN_VALUE);
                resp.setHeader("Cache-Control","max-age=31556926");
                for(Position9 position9 : position9s){
                    writer.println(position9.getPositionCode());
                }
                resp.flushBuffer();
            }
        }
    }

}
