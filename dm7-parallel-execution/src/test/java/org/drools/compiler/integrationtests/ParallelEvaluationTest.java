/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.compiler.integrationtests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.drools.core.base.ClassObjectType;
import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.reteoo.CompositePartitionAwareObjectSinkAdapter;
import org.drools.core.reteoo.EntryPointNode;
import org.drools.core.reteoo.ObjectTypeNode;
import org.drools.core.rule.EntryPointId;
import org.junit.Test;
import org.kie.api.KieBase;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.conf.MultithreadEvaluationOption;
import org.kie.internal.utils.KieHelper;
import org.redhat.gss.DebugList;

/**
 * 
 * https://github.com/kiegroup/drools/blob/master/drools-compiler/src/test/java/org/drools/compiler/integrationtests/ParallelEvaluationTest.java
 * 
 * @author osiris
 *
 */
public class ParallelEvaluationTest {

	private static final Integer NUMBER_OF_RULES = 1800;
	private static final Integer NUMBER_OF_FACTS = 400;
	private static final Integer NUMBER_OF_ITERATIONS = 250;
	private static final Boolean SERIAL = false;
	private static final Boolean PARALLEL = true;

	@Test(timeout = 100000L)
	public void test() throws InterruptedException {

		System.out.println("wait so we can start collecting thread dumps");
		Thread.sleep(10000);

		StringBuilder sb = new StringBuilder(400);
		sb.append("global java.util.List list;\n");
		for (int i = 0; i < NUMBER_OF_RULES; i++) {
			sb.append(getRule(i, ""));
		}
		System.out.println("RULE BASE:\n" + sb.toString());

		/**
		 * start parallel execution
		 */
		long parallelStart = 0L;
		long parallelDuration = 0L;
		if (PARALLEL) {
			parallelStart = System.currentTimeMillis();
			testRules(true,sb.toString(), MultithreadEvaluationOption.YES);
			parallelDuration = System.currentTimeMillis() - parallelStart;
		}

		/**
		 * start serial execution
		 */
		if (SERIAL) {
			long serialStart = System.currentTimeMillis();
			testRules(false,sb.toString(), MultithreadEvaluationOption.NO);
			long serialDuration = System.currentTimeMillis() - serialStart;

			System.out.println("serial:" + serialDuration);
		}
		if (PARALLEL)
			System.out.println("parallel:" + parallelDuration);

	}

	private String getRule(int i, String rhs) {
		return getRule(i, rhs, "");
	}

	private String getRule(int i, String rhs, String attributes) {
		return "rule R" + i + " " + attributes + "when\n" + "    $i : Integer( intValue == " + i + " )"
				+ "    String( toString == $i.toString )\n" + "then\n" + "    list.add($i);\n" + rhs + "end\n";
	}

	public static void testRules(Boolean isParallel, String rules, MultithreadEvaluationOption option) {

		KieBase kbase = new KieHelper().addContent(rules.toString(), ResourceType.DRL).build(option);

		EntryPointNode epn = ((InternalKnowledgeBase) kbase).getRete().getEntryPointNode(EntryPointId.DEFAULT);
		ObjectTypeNode otn = epn.getObjectTypeNodes().get(new ClassObjectType(Integer.class));
		if (isParallel) {
        assertTrue( ( (CompositePartitionAwareObjectSinkAdapter) otn.getObjectSinkPropagator() ).isHashed() );
		}


		List<Integer> list = new DebugList<Integer>();

		for (int counter = 0; counter < NUMBER_OF_ITERATIONS; counter++) {
			KieSession ksession = kbase.newKieSession();
			list = new DebugList<Integer>();
			ksession.setGlobal("list", list);

			for (int i = 0; i < NUMBER_OF_FACTS; i++) {
				ksession.insert(i);
				ksession.insert("" + i);
			}

			ksession.fireAllRules();

			ksession.dispose();

		}
	}

}