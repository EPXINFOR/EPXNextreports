/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ro.nextreports.server.web.core.migration;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.content.Folder;
import org.apache.wicket.extensions.markup.html.repeater.tree.theme.WindowsTheme;
import org.apache.wicket.extensions.markup.html.repeater.util.ProviderSubset;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import ro.nextreports.server.StorageConstants;
import ro.nextreports.server.domain.Entity;
import ro.nextreports.server.exception.NotFoundException;
import ro.nextreports.server.service.ReportService;
import ro.nextreports.server.service.StorageService;
import ro.nextreports.server.util.EntityComparator;
import ro.nextreports.server.web.common.form.FormContentPanel;
import ro.nextreports.server.web.common.form.FormPanel;
import ro.nextreports.server.web.core.tree.EntityTreeProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Mihai Dinca-Panaitescu
 * @date 18.04.2013
 */
public class AddEntityPanel extends FormContentPanel {

	private static final long serialVersionUID = 1L;

	@SpringBean
	private StorageService storageService;

    @SpringBean
    private ReportService reportService;

	private MigrationEntityType type;
//	private Entity entity;

	private Component swapComponent;
	private ITreeProvider<Entity> treeProvider;
	private EntityTree tree;
	private DropDownChoice<MigrationEntityType> typeDropDownChoice;

	private ProviderSubset<Entity> selected;

	public AddEntityPanel() {
		super(FormPanel.CONTENT_ID);

		final EmptyPanel emptyTree = new EmptyPanel("tree");
		emptyTree.setOutputMarkupId(true);
		swapComponent = emptyTree;
		swapComponent.setOutputMarkupId(true);
		add(swapComponent);

		List<MigrationEntityType> types = new ArrayList<MigrationEntityType>();
		types.addAll(Arrays.asList(MigrationEntityType.values()));
		IChoiceRenderer<MigrationEntityType> renderer = new ChoiceRenderer<MigrationEntityType> () {

			private static final long serialVersionUID = 1L;

			@Override
			public Object getDisplayValue(MigrationEntityType  object) {
				return getString(object.toString());
			}

			@Override
			public String getIdValue(MigrationEntityType object, int index) {
				return object.toString();
			}

	    };
		typeDropDownChoice = new DropDownChoice<MigrationEntityType>("type",
				new PropertyModel<MigrationEntityType>(this, "type"), types, renderer);
		typeDropDownChoice.setOutputMarkupPlaceholderTag(true);
		add(typeDropDownChoice);

		typeDropDownChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				if (type == null) {
					if (tree != null) {
						swapComponent.replaceWith(emptyTree);
						swapComponent = emptyTree;
						tree = null;
					}
				} else {
					tree = createTree(getRootPath());
					tree.setOutputMarkupId(true);
					swapComponent.replaceWith(tree);
					swapComponent = tree;
				}
				target.add(swapComponent);
				target.add(getFeedbackPanel());
			}

		});
	}

	public Iterator<Entity> getEntities() {
		if (selected == null) {
			return Collections.emptyIterator();
		}
		return selected.iterator();
	}

	protected EntityTree createTree(String rootPath) {
		treeProvider = new EntityTreeProvider(rootPath) {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<Entity> getChildren(String id) throws NotFoundException {
				// sort
				List<Entity> children = super.getChildren(id);
				Collections.sort(children, new EntityComparator());

				return children;
			}

		};

        selected = new ProviderSubset<Entity>(treeProvider, false);

		return new EntityTree("tree", treeProvider);
	}

    protected boolean isSelected(Entity entity) {
        return selected.contains(entity);
    }

    protected void toggle(Entity entity, AjaxRequestTarget target) {
        if (isSelected(entity)) {
            selected.remove(entity);
        } else {
            selected.add(entity);
        }

        tree.updateNode(entity, target);
    }

	private String getRootPath() {
		String rootPath;
		if (MigrationEntityType.CHART.equals(type)) {
			rootPath = StorageConstants.CHARTS_ROOT;
		} else if (MigrationEntityType.REPORT.equals(type)) {
			rootPath = StorageConstants.REPORTS_ROOT;
		} else if (MigrationEntityType.DATASOURCE.equals(type)) {
			rootPath = StorageConstants.DATASOURCES_ROOT;
		} else {
			rootPath = StorageConstants.DASHBOARDS_ROOT;
		}

		return rootPath;
	}

	private class EntityTree extends NestedTree<Entity> {

    	private static final long serialVersionUID = 1L;

    	public EntityTree(String id, ITreeProvider<Entity> provider) {
			super(id, provider);

    		add(new WindowsTheme());
		}

		@Override
		protected Component newContentComponent(String id, IModel<Entity> model) {
			return new Folder<Entity>(id, this, model) {

    			private static final long serialVersionUID = 1L;

    			@Override
    			protected boolean isClickable() {
    				return true;
    			}

                @Override
				protected String getOtherStyleClass(Entity t) {
                	if (t instanceof ro.nextreports.server.domain.Folder) {
                		return getClosedStyleClass();
                	}

                	return super.getOtherStyleClass(t);
				}

    			@Override
    			protected void onClick(AjaxRequestTarget target) {
                    super.onClick(target);

                    if (getModelObject() instanceof ro.nextreports.server.domain.Folder) {
                        return;
                    }

                    AddEntityPanel.this.toggle(getModelObject(), target);

                    target.add(getFeedbackPanel());
    			}

    			@Override
				protected boolean isSelected() {
   	                return AddEntityPanel.this.isSelected(getModelObject());
				}

    			@Override
    			protected IModel<?> newLabelModel(IModel<Entity> model) {
    				return Model.of(model.getObject().getName());
    			}

    		};
		}

	}
	
	public void setRoot(MigrationEntityType type) {
		if (type == null) {
			return;
		}
		this.type = type;
		typeDropDownChoice.setEnabled(false);
		tree = createTree(getRootPath());
		tree.setOutputMarkupId(true);
		swapComponent.replaceWith(tree);
		swapComponent = tree;			
	}
	
	public Set<Entity> getTreeModel() {
		return tree.getModelObject();
	}
	
}

