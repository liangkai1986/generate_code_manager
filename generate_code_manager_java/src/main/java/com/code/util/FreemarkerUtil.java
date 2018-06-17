package com.code.util;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerUtil {
	public static void main(String[] args) throws Exception {
		Map data = new HashMap<String, Object>();
		data.put("user", "哈哈哈");
		String a = createFile("欢迎：${user}", data, "G:\\aa.txt");
		System.out.println(a);
	}

	public static String createFile(String templateStr, Map data, String path) {
		try {
			Configuration cfg = new Configuration();
			cfg.setTemplateLoader(new StringTemplateLoader(templateStr));
			cfg.setDefaultEncoding("UTF-8");

			Template template = cfg.getTemplate("");

			StringWriter writer = new StringWriter();
			template.process(data, writer);

			FileWriter fileWriter = new FileWriter(path);
			template.process(data, fileWriter);
			
			return writer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	static class StringTemplateLoader implements TemplateLoader {

		private String template;

		public StringTemplateLoader(String template) {
			this.template = template;
			if (template == null) {
				this.template = "";
			}
		}

		public void closeTemplateSource(Object templateSource) throws IOException {
			((StringReader) templateSource).close();
		}

		public Object findTemplateSource(String name) throws IOException {
			return new StringReader(template);
		}

		public long getLastModified(Object templateSource) {
			return 0;
		}

		public Reader getReader(Object templateSource, String encoding) throws IOException {
			return (Reader) templateSource;
		}

	}
}
