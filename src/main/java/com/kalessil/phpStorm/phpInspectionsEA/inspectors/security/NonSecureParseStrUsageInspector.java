package com.kalessil.phpStorm.phpInspectionsEA.inspectors.security;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.lang.psi.elements.FunctionReference;
import com.kalessil.phpStorm.phpInspectionsEA.openApi.BasePhpElementVisitor;
import com.kalessil.phpStorm.phpInspectionsEA.openApi.BasePhpInspection;
import org.jetbrains.annotations.NotNull;

/*
 * This file is part of the Php Inspections (EA Extended) package.
 *
 * (c) Vladimir Reznichenko <kalessil@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 */

public class NonSecureParseStrUsageInspector  extends BasePhpInspection {
    private static final String message = "Please provide second parameter to not influence globals.";

    @NotNull
    public String getShortName() {
        return "NonSecureParseStrUsageInspection";
    }

    @Override
    @NotNull
    public PsiElementVisitor buildVisitor(@NotNull final ProblemsHolder holder, boolean isOnTheFly) {
        return new BasePhpElementVisitor() {
            @Override
            public void visitPhpFunctionCall(@NotNull FunctionReference reference) {
                final String functionName = reference.getName();
                if (functionName != null && (functionName.equals("parse_str") || functionName.equals("mb_parse_str"))) {
                    final PsiElement[] arguments = reference.getParameters();
                    if (arguments.length == 1) {
                        holder.registerProblem(reference, message, ProblemHighlightType.GENERIC_ERROR);
                    }
                }
                /* TODO: call_user_func, array_walk - dynamic contexts */
            }
        };
    }
}
