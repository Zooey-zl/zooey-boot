package com.cn.zooey;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.cn.zooey.entity.User;
import com.cn.zooey.service.UserService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@SpringBootTest
class ZooeyBootApplicationTests {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


        String pwd = "123456.zz";

        String encode = passwordEncoder.encode(pwd);

        System.out.println(bCryptPasswordEncoder.encode(pwd));

        System.out.println(encode);
    }

    @Resource
    private ElasticsearchClient esClient;

    /**
     * 创建索引
     * @throws IOException
     */
    @Test
    void testCreatIndex() throws IOException {
        log.info("client == {}", esClient);
        CreateIndexResponse response = esClient.indices().create(c -> c.index("ik_test"));
        log.info("response == {}", response);
    }

    /**
     * 索引是否存在
     * @throws IOException
     */
    @Test
    void testExistIndex() throws IOException {
        BooleanResponse response = esClient.indices().exists(c -> c.index("ik_test"));
        log.info("response == {}", response);
        log.info("response == {}", response.value());
    }

    /**
     * 索引列表
     * @throws IOException
     */
    @Test
    void testIndexList() throws IOException {
        // 使用 * 或者 _all 都可以
        GetIndexResponse response = esClient.indices().get(c -> c.index("_all"));
        log.info("response == {}", response);
        log.info("response == {}", response.result());
    }

    /**
     * 索引查询
     * @throws IOException
     */
    @Test
    void testGetIndex() throws IOException {
        GetIndexResponse response = esClient.indices().get(c -> c.index("ik_test"));
        log.info("response == {}", response);
        log.info("response == {}", response.result());
    }

    @Resource
    private UserService userService;

    /**
     * 创建文档
     * @throws IOException
     */
    @Test
    void testCreateDoc() throws IOException {
        User user = userService.getById(1);
        IndexResponse response = esClient.index(c -> c
                .index("ik_test")
                .id(user.getId().toString())
                .document(user));
        log.info("response == {}", response);
        log.info("response == {}", response.result());
    }

    /**
     * 更新文档
     * @throws IOException
     */
    @Test
    void testUpdateDoc() throws IOException {
        User user = userService.getById(2);
        UpdateResponse<User> response = esClient.update(u -> u
                        .index("user")
                        .id("1")
                        .doc(user)
                , User.class);
        log.info("response == {}", response);
        log.info("response == {}", response.result());
    }

    /**
     * 文档是否存在
     * @throws IOException
     */
    @Test
    void testExistDoc() throws IOException {
        BooleanResponse response = esClient.exists(c -> c
                .index("ik_test")
                .id("2"));
        log.info("response == {}", response);
        log.info("response == {}", response.value());
    }

    /**
     * 查询文档 返回实体
     * 实体父类的字段值未展示,解决方案:
     * 原因是因为子类的@Data 注解包含的 toString 方法不包含父类的属性
     * 子类添加@ToString(callSuper = true) 注解解决问题
     * @throws IOException
     */
    @Test
    void testGetDoc() throws IOException {
        GetResponse<User> response = esClient.get(c -> c
                        .index("ik_test")
                        .id("2")
                , User.class);
        if (!response.found()) {
            log.info("未找到对应数据");
            return;
        }
        User user = response.source();
        log.info("response == {}", user != null ? user.getCreateTime() : -1);
        log.info("response == {}", response);
        log.info("response == {}", response.source());
    }

    /**
     * 查询文档 返回 JSON
     * @throws IOException
     */
    @Test
    void testGetDocJson() throws IOException {
        GetResponse<ObjectNode> response = esClient.get(c -> c
                        .index("ik_test")
                        .id("2")
                , ObjectNode.class);
        log.info("response == {}", response);
        log.info("response == {}", response.source());
    }

    /**
     * 查询
     * @throws IOException
     */
    @Test
    void testSearchDoc() throws IOException {
        SearchResponse<User> response = esClient.search(c -> c
                        .index("ik_test")
                        .query(q -> q
                                .match(t -> t
                                        .field("userName")
                                        .query("Zooey")))
                        .from(0)
                        .size(10)
                        .sort(s -> s.field(f -> f.field("id").order(SortOrder.Desc)))
                , User.class);
        log.info("response == {}", response);
        log.info("response == {}", response.hits().hits());
    }


}
