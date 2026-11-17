package cl.duoc.test.api;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
@Service public class TokenService {
 private final ConcurrentHashMap<String,String> sessions=new ConcurrentHashMap<>();
 public String issue(String userId){String token=UUID.randomUUID().toString();sessions.put(token,userId);return token;}
 public String userId(String token){return sessions.get(token);}
 public void revoke(String token){sessions.remove(token);}
}
