package yozi.mall.product.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import yozi.mall.product.entity.CategoryEntity;
import yozi.mall.product.service.CategoryService;
import yozi.mall.product.vo.Catalogs2Vo;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Resource
    private CategoryService categoryService;

   /* @Autowired
    private RedissonClient redisson;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
*/

    @GetMapping(value = {"/", "index.html"})
    private String indexPage(Model model) {
        //1、查出所有的一級分類
        List<CategoryEntity> categoryEntities = categoryService.getLevel1Catalog();
        model.addAttribute("categories", categoryEntities);
        return "index";
    }

    /**
     * 獲取所有分類，且二、三級分類以封裝好
     *
     * @return
     */
    @GetMapping(value = "/index/catalog.json")
    @ResponseBody
    public Map<String, List<Catalogs2Vo>> getCatalogJson() {
        return categoryService.getCatalogJson();
    }


    /*@ResponseBody
    @GetMapping(value = "/hello")
    public String hello() {

        //1、獲取一把鎖，只要鎖的名字一樣，就是同一把鎖
        RLock myLock = redisson.getLock("my-lock");

        //2、加鎖
        myLock.lock();      //阻塞式等待。默認加的鎖都是30s
        //1）、鎖的自動續期，如果業務超長，運行期間自動鎖上新的30s。不用擔心業務時間長，鎖自動過期被刪掉
        //2）、加鎖的業務只要運行完成，就不會給當前鎖續期，即使不手動解鎖，鎖默認會在30s內自動過期，不會產生死鎖問題
        // myLock.lock(10,TimeUnit.SECONDS);   //10秒鐘自動解鎖,自動解鎖時間一定要大於業務執行時間
        //問題：在鎖時間到了以後，不會自動續期
        //1、如果我們傳遞了鎖的超時時間，就發送給redis執行腳本，進行占鎖，默認超時就是 我們制定的時間
        //2、如果我們指定鎖的超時時間，就使用 lockWatchdogTimeout = 30 * 1000 【看門狗默認時間】
        //只要佔鎖成功，就會啟動一個定時任務【重新給鎖設置過期時間，新的過期時間就是看門狗的默認時間】,每隔10秒都會自動的再次續期，續成30秒
        // internalLockLeaseTime 【看門狗時間】 / 3， 10s
        try {
            System.out.println("加鎖成功，執行業務..." + Thread.currentThread().getId());
            try { TimeUnit.SECONDS.sleep(20); } catch (InterruptedException e) { e.printStackTrace(); }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            //3、解鎖  假設解鎖代碼沒有運行，Redisson會不會出現死鎖
            System.out.println("釋放鎖..." + Thread.currentThread().getId());
            myLock.unlock();
        }

        return "hello";
    }*/

    /**
     * 保證一定能讀到最新數據，修改期間，寫鎖是一個排它鎖（互斥鎖、獨享鎖），讀鎖是一個共享鎖
     * 寫鎖沒釋放讀鎖必須等待
     * 讀 + 讀 ：相當於無鎖，併發讀，只會在Redis中記錄好，所有當前的讀鎖。他們都會同時加鎖成功
     * 寫 + 讀 ：必須等待寫鎖釋放
     * 寫 + 寫 ：阻塞方式
     * 讀 + 寫 ：有讀鎖。寫也需要等待
     * 只要有讀或者寫的存都必須等待
     * @return
     *//*
    @GetMapping(value = "/write")
    @ResponseBody
    public String writeValue() {
        String s = "";
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("rw-lock");
        RLock rLock = readWriteLock.writeLock();
        try {
            //1、改數據加寫鎖，讀數據加讀鎖
            rLock.lock();
            s = UUID.randomUUID().toString();
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            ops.set("writeValue",s);
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }

        return s;
    }*/

    /*@GetMapping(value = "/read")
    @ResponseBody
    public String readValue() {
        String s = "";
        RReadWriteLock readWriteLock = redisson.getReadWriteLock("rw-lock");
        //加讀鎖
        RLock rLock = readWriteLock.readLock();
        try {
            rLock.lock();
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            s = ops.get("writeValue");
            try { TimeUnit.SECONDS.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }

        return s;
    }
*/

    /**
     * 車庫停車
     * 3車位
     * 信號量也可以做分佈式限流
     */
    /*@GetMapping(value = "/park")
    @ResponseBody
    public String park() throws InterruptedException {

        RSemaphore park = redisson.getSemaphore("park");
        park.acquire();     //獲取一個信號、獲取一個值,佔一個車位
        boolean flag = park.tryAcquire();

        if (flag) {
            //執行業務
        } else {
            return "error";
        }

        return "ok=>" + flag;
    }

    @GetMapping(value = "/go")
    @ResponseBody
    public String go() {
        RSemaphore park = redisson.getSemaphore("park");
        park.release();     //釋放一個車位
        return "ok";
    }*/

    /**
     * 放假、鎖門
     * 1班沒人了
     * 5個班，全部走完，我們才可以鎖大門
     * 分佈式閉鎖
     */

    /*@GetMapping(value = "/lockDoor")
    @ResponseBody
    public String lockDoor() throws InterruptedException {

        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.trySetCount(5);
        door.await();       //等待閉鎖完成

        return "放假了...";
    }*/

    /*@GetMapping(value = "/gogogo/{id}")
    @ResponseBody
    public String gogogo(@PathVariable("id") Long id) {
        RCountDownLatch door = redisson.getCountDownLatch("door");
        door.countDown();       //計數-1

        return id + "班的人都走了...";
    }*/

}
