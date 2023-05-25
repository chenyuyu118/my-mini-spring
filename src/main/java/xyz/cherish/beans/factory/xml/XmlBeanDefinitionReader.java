package xyz.cherish.beans.factory.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import xyz.cherish.beans.factory.config.BeanDefinition;
import xyz.cherish.beans.factory.config.BeanReference;
import xyz.cherish.beans.factory.support.AbstractBeanDefinitionReader;
import xyz.cherish.beans.factory.support.BeanDefinitionRegistry;
import xyz.cherish.core.io.Resource;
import xyz.cherish.core.io.ResourceLoader;
import xyz.cherish.exception.BeansException;
import xyz.cherish.utils.StrUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
    public static final String BEAN_ELEMENT = "bean";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String ID_ATTRIBUTE = "id";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String REF_ATTRIBUTE = "ref";

    public static final String INIT_METHOD_ATTRIBUTE = "init-method";
    public static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";
    public static final String SCOPE_ATTRIBUTE = "scope";

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinition(Resource resource) throws BeansException {
        try (InputStream xmlIs = resource.getInputStream()) {
            doLoadBeanDefinition(xmlIs);
        } catch (IOException | ParserConfigurationException | SAXException ex) {
            throw new BeansException("IOException in parse xml file", ex);
        }
    }

    /**
     * 解析xml输入流未Bean定义
     * @param xmlIs xml 输入流
     * @throws ParserConfigurationException 解析错误
     * @throws IOException IO错误
     * @throws SAXException sax错误
     */
    private void doLoadBeanDefinition(InputStream xmlIs) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document xmlConfigurationDoc = documentBuilder.parse(xmlIs);
        Element rootEle = xmlConfigurationDoc.getDocumentElement();
        NodeList eleList = rootEle.getChildNodes();
        int eleListLength = eleList.getLength();
        for (int i = 0; i < eleListLength; ++i) {
            Node item = eleList.item(i);
            if (item instanceof Element) {
                // 当为一个element时，判断element的标签类型进行不同处理
                if (BEAN_ELEMENT.equals(item.getNodeName())) {
                    doLoadOneBean((Element) item);
                }
            }
        }
    }

    private void doLoadOneBean(Element beanEle) {
        String idAttr = beanEle.getAttribute(ID_ATTRIBUTE);
        String nameAttr = beanEle.getAttribute(NAME_ATTRIBUTE);
        String clazzName = beanEle.getAttribute(CLASS_ATTRIBUTE);
        String initMethod = beanEle.getAttribute(INIT_METHOD_ATTRIBUTE);
        String destroyMethod = beanEle.getAttribute(DESTROY_METHOD_ATTRIBUTE);
        String scope = beanEle.getAttribute(SCOPE_ATTRIBUTE);
        /*
        获取bean的class对象
         */
        Class<?> clazz = null;
        try {
            clazz = Class.forName(clazzName);
        } catch (ClassNotFoundException classNotFoundException) {
            throw new BeansException("can't find class named: " + clazzName, classNotFoundException);
        }
        /*
        bean的注册名称
         */
        String beanName = idAttr.isEmpty() ? nameAttr : idAttr;
        if (beanName.isEmpty()) {
            beanName = StrUtils.lowerFirst(clazz.getSimpleName());
        }
        /*
        创建bean定义
         */
        BeanDefinition beanDefinition = new BeanDefinition(clazz);
        beanDefinition.setInitMethodName(initMethod.isEmpty() ? "" : initMethod);
        beanDefinition.setDestroyMethodName(initMethod.isEmpty() ? "" : destroyMethod);
        beanDefinition.setScope(scope);
        /*
        初始化property
         */
        NodeList propertiesNodes = beanEle.getChildNodes();
        int propertiesNodesLength = propertiesNodes.getLength();
        for (int i = 0; i < propertiesNodesLength; ++i) {
            Node item = propertiesNodes.item(i);
            if (item instanceof Element property) {
                if (PROPERTY_ELEMENT.equals(property.getNodeName())) {
                    String propertyName = property.getAttribute(NAME_ATTRIBUTE);
                    String propertyValue = property.getAttribute(VALUE_ATTRIBUTE);
                    String propertyRef = property.getAttribute(REF_ATTRIBUTE);
                    if (propertyName.isEmpty()) {
                        throw new BeansException("The attribute can't be null or empty");
                    }
                    /*
                    为属性赋值，引用值或者固定值
                     */
                    Object value = propertyValue;
                    if (!propertyRef.isEmpty()) {
                        value = new BeanReference(propertyRef);
                    }
                    beanDefinition.addPropertyValue(propertyName, value);
                }
            }
        }
        /*
        实际注册Bean
         */
        if (getRegistry().containBeanDefinition(beanName)) {
            throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
        }
        getRegistry().registerBeanDefinition(beanName, beanDefinition);
    }
}
