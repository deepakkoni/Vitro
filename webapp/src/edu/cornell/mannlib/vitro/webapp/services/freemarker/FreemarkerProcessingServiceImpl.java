/* $This file is distributed under the terms of the license in /doc/license.txt$ */

package edu.cornell.mannlib.vitro.webapp.services.freemarker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.cornell.mannlib.vitro.webapp.controller.VitroRequest;
import edu.cornell.mannlib.vitro.webapp.controller.freemarker.FreemarkerConfiguration;
import edu.cornell.mannlib.vitro.webapp.controller.freemarker.FreemarkerConfigurationLoader;
import edu.cornell.mannlib.vitro.webapp.utils.log.LogUtils;
import freemarker.core.Environment;
import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * An implementation of the FreemarkerProcessingService.
 */
public class FreemarkerProcessingServiceImpl implements
		FreemarkerProcessingService {
	private static final Log log = LogFactory
			.getLog(FreemarkerProcessingServiceImpl.class);

	@Override
	public boolean isTemplateAvailable(String templateName,
			HttpServletRequest req) throws TemplateProcessingException {
		return null != getTemplate(templateName, req);
	}

	@Override
	public String renderTemplate(String templateName, Map<String, Object> map,
			HttpServletRequest req) throws TemplateProcessingException {
		log.debug("renderTemplate: '" + templateName + "' with "
				+ LogUtils.deepFormatForLog(log, "debug", map));
		Template template = getTemplate(templateName, req);
		return processTemplate(template, map, req);
	}

	/**
	 * Fetch this template from a file and parse it. If there are any problems,
	 * return "null".
	 */
	private Template getTemplate(String templateName, HttpServletRequest req)
			throws TemplateProcessingException {
		Template template = null;
		try {
			Configuration config = FreemarkerConfigurationLoader
					.getConfig(new VitroRequest(req));
			template = config.getTemplate(templateName);
		} catch (ParseException e) {
			log.warn("Failed to parse the template at '" + templateName + "'"
					+ e);
			throw new TemplateParsingException(e);
		} catch (FileNotFoundException e) {
			log.debug("No template found for '" + templateName + "'");
			throw new TemplateProcessingException(e);
		} catch (IOException e) {
			log.warn("Failed to read the template at '" + templateName + "'", e);
			throw new TemplateProcessingException(e);
		}
		return template;
	}

	private String processTemplate(Template template, Map<String, Object> map,
			HttpServletRequest req) throws TemplateProcessingException {

		StringWriter writer = new StringWriter();
		try {
			// Add directives to the map. For some reason, having them in the
			// configuration is not enough.
			map.putAll(FreemarkerConfiguration.getDirectives());

			// Add request and servlet context as custom attributes of the
			// environment, so they
			// can be used in directives.
			Environment env = template.createProcessingEnvironment(map, writer);
			env.setCustomAttribute("request", req);
			env.setCustomAttribute("context", req.getSession()
					.getServletContext());
			env.process();
			return writer.toString();
		} catch (TemplateException e) {
			throw new TemplateProcessingException(
					"TemplateException creating processing environment", e);
		} catch (IOException e) {
			throw new TemplateProcessingException(
					"IOException creating processing environment", e);
		}
	}
}
