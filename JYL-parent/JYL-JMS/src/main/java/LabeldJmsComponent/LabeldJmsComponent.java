package LabeldJmsComponent;


import java.util.Map;

import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;


/**
 * <p>功能描述:</br> TODO</p>
 * 
 * @className LabeldJmsComponent
 * @author jiangyu
 * @date 2016年4月3日 下午5:54:40
 * @version v1.0
 */
public class LabeldJmsComponent extends JmsComponent
{

    private String label = "local";

    @Override
    protected String convertPathToActualDestination(String path, Map<String, Object> parameters)
    {
        return super.convertPathToActualDestination(path + "-" + label, parameters);
    }

    public LabeldJmsComponent()
    {
        super();
    }

    public LabeldJmsComponent(CamelContext context)
    {
        super(context);
    }

    public LabeldJmsComponent(JmsConfiguration configuration)
    {
        super(configuration);
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

}
