/* ###
 * IP: GHIDRA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ghidra.app.util.pdb.pdbapplicator;

import ghidra.app.util.bin.format.pdb2.pdbreader.MsSymbolIterator;
import ghidra.app.util.bin.format.pdb2.pdbreader.symbol.*;

/**
 * Pseudo-factory for creating the {@link MsSymbolApplier} for the {@link AbstractMsSymbol}
 * indicated by the {@link MsSymbolIterator}.
 */
public class SymbolApplierFactory {

	private DefaultPdbApplicator applicator;

	SymbolApplierFactory(DefaultPdbApplicator applicator) {
		this.applicator = applicator;
	}

	MsSymbolApplier getSymbolApplier(MsSymbolIterator iter) {
		AbstractMsSymbol symbol = iter.peek();
		if (symbol == null) {
			applicator.appendLogMsg("PDB Warning: No AbstractMsSymbol");
			return null;
		}
		return getSymbolApplier(symbol, iter);
	}

	MsSymbolApplier getSymbolApplier(AbstractMsSymbol symbol, MsSymbolIterator iter) {
		MsSymbolApplier applier = getSymbolApplierInternal(symbol, iter);
		if (applier instanceof NoSymbolApplier) {
			applicator.getPdbApplicatorMetrics().witnessCannotApplySymbolType(symbol);
		}
		return applier;
	}

	private MsSymbolApplier getSymbolApplierInternal(AbstractMsSymbol symbol,
			MsSymbolIterator iter) {
		MsSymbolApplier applier = null;

		int pdbId = symbol.getPdbId();
		switch (pdbId) {
//				// 0x0000 block
//				case CompileFlagsMsSymbol.PDB_ID:
//					symbol = new CompileFlagsMsSymbol(pdb, reader);
//					break;
//				case Register16MsSymbol.PDB_ID:
//					symbol = new Register16MsSymbol(pdb, reader);
//					break;
//				case Constant16MsSymbol.PDB_ID:
//					symbol = new Constant16MsSymbol(pdb, reader);
//					break;
			case UserDefinedType16MsSymbol.PDB_ID:
				applier =
					new TypedefSymbolApplier(applicator, (AbstractUserDefinedTypeMsSymbol) symbol);
				break;
//				case StartSearchMsSymbol.PDB_ID:
//					symbol = new StartSearchMsSymbol(pdb, reader);
//					break;
			case EndMsSymbol.PDB_ID:
				applier = new EndSymbolApplier(applicator, (EndMsSymbol) symbol);
				break;
//				case SkipMsSymbol.PDB_ID:
//					symbol = new SkipMsSymbol(pdb, reader);
//					break;
//				case CvReservedMsSymbol.PDB_ID:
//					symbol = new CvReservedMsSymbol(pdb, reader);
//					break;
//				case ObjectNameStMsSymbol.PDB_ID:
//					symbol = new ObjectNameStMsSymbol(pdb, reader);
//					break;
//				case EndArgumentsListMsSymbol.PDB_ID:
//					symbol = new EndArgumentsListMsSymbol(pdb, reader);
//					break;
			case CobolUserDefinedType16MsSymbol.PDB_ID:
				applier =
					new TypedefSymbolApplier(applicator, (AbstractUserDefinedTypeMsSymbol) symbol);
				break;
//				case ManyRegisterVariable16MsSymbol.PDB_ID:
//					symbol = new ManyRegisterVariable16MsSymbol(pdb, reader);
//					break;
//				case ReturnDescriptionMsSymbol.PDB_ID:
//					symbol = new ReturnDescriptionMsSymbol(pdb, reader);
//					break;
//				case EntryThisMsSymbol.PDB_ID:
//					symbol = new EntryThisMsSymbol(pdb, reader);
//					break;
//
//				// 0x0100 block
//				case BasePointerRelative16MsSymbol.PDB_ID:
//					symbol = new BasePointerRelative16MsSymbol(pdb, reader);
//					break;
			case LocalData16MsSymbol.PDB_ID:
				applier = new DataSymbolApplier(applicator, (AbstractDataMsSymbol) symbol);
				break;
			case GlobalData16MsSymbol.PDB_ID:
				applier = new DataSymbolApplier(applicator, (AbstractDataMsSymbol) symbol);
				break;
			case Public16MsSymbol.PDB_ID:
				applier = new PublicSymbolApplier(applicator, (AbstractPublicMsSymbol) symbol);
				break;
			case LocalProcedureStart16MsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case GlobalProcedureStart16MsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case Thunk16MsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case Block16MsSymbol.PDB_ID:
				applier = new BlockSymbolApplier(applicator, (AbstractBlockMsSymbol) symbol);
				break;
			case With16MsSymbol.PDB_ID:
				applier = new WithSymbolApplier(applicator, (AbstractWithMsSymbol) symbol);
				break;
			case Label16MsSymbol.PDB_ID:
				applier = new LabelSymbolApplier(applicator, (AbstractLabelMsSymbol) symbol);
				break;
//				case ChangeExecutionModel16MsSymbol.PDB_ID:
//					symbol = new ChangeExecutionModel16MsSymbol(pdb, reader);
//					break;
//				case VirtualFunctionTable16MsSymbol.PDB_ID:
//					symbol = new VirtualFunctionTable16MsSymbol(pdb, reader);
//					break;
			case RegisterRelativeAddress16MsSymbol.PDB_ID:
				applier = new RegisterRelativeSymbolApplier(applicator,
					(AbstractRegisterRelativeAddressMsSymbol) symbol);
				break;
//
//				// 0x0200 block
//				case BasePointerRelative3216MsSymbol.PDB_ID:
//					symbol = new BasePointerRelative3216MsSymbol(pdb, reader);
//					break;
			case LocalData3216MsSymbol.PDB_ID:
				applier = new DataSymbolApplier(applicator, (AbstractDataMsSymbol) symbol);
				break;
			case GlobalData3216MsSymbol.PDB_ID:
				applier = new DataSymbolApplier(applicator, (AbstractDataMsSymbol) symbol);
				break;
			case Public3216MsSymbol.PDB_ID:
				applier = new PublicSymbolApplier(applicator, (AbstractPublicMsSymbol) symbol);
				break;
			case LocalProcedureStart3216MsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case GlobalProcedureStart3216MsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case Thunk32StMsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case Block32StMsSymbol.PDB_ID:
				applier = new BlockSymbolApplier(applicator, (AbstractBlockMsSymbol) symbol);
				break;
			case With32StMsSymbol.PDB_ID:
				applier = new WithSymbolApplier(applicator, (AbstractWithMsSymbol) symbol);
				break;
			case Label32StMsSymbol.PDB_ID:
				applier = new LabelSymbolApplier(applicator, (AbstractLabelMsSymbol) symbol);
				break;
//				case ChangeExecutionModel32MsSymbol.PDB_ID:
//					symbol = new ChangeExecutionModel32MsSymbol(pdb, reader);
//					break;
//				case VirtualFunctionTable3216MsSymbol.PDB_ID:
//					symbol = new VirtualFunctionTable3216MsSymbol(pdb, reader);
//					break;
			case RegisterRelativeAddress3216MsSymbol.PDB_ID:
				applier = new RegisterRelativeSymbolApplier(applicator,
					(AbstractRegisterRelativeAddressMsSymbol) symbol);
				break;
//				case LocalThreadStorage3216MsSymbol.PDB_ID:
//					symbol = new LocalThreadStorage3216MsSymbol(pdb, reader);
//					break;
//				case GlobalThreadStorage3216MsSymbol.PDB_ID:
//					symbol = new GlobalThreadStorage3216MsSymbol(pdb, reader);
//					break;
//				case StaticLinkForMipsExceptionHandlingMsSymbol.PDB_ID:
//					symbol = new StaticLinkForMipsExceptionHandlingMsSymbol(pdb, reader);
//					break;

			// 0x0300 block
			case LocalProcedureStartMips16MsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case GlobalProcedureStartMips16MsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;

			// 0x0400 block
			case ProcedureReferenceStMsSymbol.PDB_ID:
				applier =
					new ReferenceSymbolApplier(applicator, (AbstractReferenceMsSymbol) symbol);
				break;
			case DataReferenceStMsSymbol.PDB_ID:
				applier =
					new ReferenceSymbolApplier(applicator, (AbstractReferenceMsSymbol) symbol);
				break;
//				case AlignMsSymbol.PDB_ID:
//					symbol = new AlignMsSymbol(pdb, reader);
//					break;
			case LocalProcedureReferenceStMsSymbol.PDB_ID:
				applier =
					new ReferenceSymbolApplier(applicator, (AbstractReferenceMsSymbol) symbol);
				break;
			case OemDefinedMsSymbol.PDB_ID:
				applier = new OemDefinedSymbolApplier(applicator, (OemDefinedMsSymbol) symbol);
				break;
//
//				// 0x1000 block
//				case RegisterStMsSymbol.PDB_ID:
//					symbol = new RegisterStMsSymbol(pdb, reader);
//					break;
//				case ConstantStMsSymbol.PDB_ID:
//					symbol = new ConstantStMsSymbol(pdb, reader);
//					break;
			case UserDefinedTypeStMsSymbol.PDB_ID:
				applier =
					new TypedefSymbolApplier(applicator, (AbstractUserDefinedTypeMsSymbol) symbol);
				break;
			case CobolUserDefinedTypeStMsSymbol.PDB_ID:
				applier =
					new TypedefSymbolApplier(applicator, (AbstractUserDefinedTypeMsSymbol) symbol);
				break;
//				case ManyRegisterVariableStMsSymbol.PDB_ID:
//					symbol = new ManyRegisterVariableStMsSymbol(pdb, reader);
//					break;
//				case BasePointerRelative32StMsSymbol.PDB_ID:
//					symbol = new BasePointerRelative32StMsSymbol(pdb, reader);
//					break;
			case LocalData32StMsSymbol.PDB_ID:
				applier = new DataSymbolApplier(applicator, (AbstractDataMsSymbol) symbol);
				break;
			case GlobalData32StMsSymbol.PDB_ID:
				applier = new DataSymbolApplier(applicator, (AbstractDataMsSymbol) symbol);
				break;
			case Public32StMsSymbol.PDB_ID:
				applier = new PublicSymbolApplier(applicator, (AbstractPublicMsSymbol) symbol);
				break;
			case LocalProcedureStart32StMsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case GlobalProcedureStart32StMsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
//				case VirtualFunctionTable32MsSymbol.PDB_ID:
//					symbol = new VirtualFunctionTable32MsSymbol(pdb, reader);
//					break;
			case RegisterRelativeAddress32StMsSymbol.PDB_ID:
				applier = new RegisterRelativeSymbolApplier(applicator,
					(AbstractRegisterRelativeAddressMsSymbol) symbol);
				break;
//				case LocalThreadStorage32StMsSymbol.PDB_ID:
//					symbol = new LocalThreadStorage32StMsSymbol(pdb, reader);
//					break;
//				case GlobalThreadStorage32StMsSymbol.PDB_ID:
//					symbol = new GlobalThreadStorage32StMsSymbol(pdb, reader);
//					break;
			case LocalProcedureStartMipsStMsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case GlobalProcedureStartMipsStMsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case ExtraFrameAndProcedureInformationMsSymbol.PDB_ID:
				applier = new FrameAndProcedureInformationSymbolApplier(applicator,
					(ExtraFrameAndProcedureInformationMsSymbol) symbol);
				break;
//				case Compile2StMsSymbol.PDB_ID:
//					symbol = new Compile2StMsSymbol(pdb, reader);
//					break;
//				case ManyRegisterVariable2StMsSymbol.PDB_ID:
//					symbol = new ManyRegisterVariable2StMsSymbol(pdb, reader);
//					break;
			case LocalProcedureStartIa64StMsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case GlobalProcedureStartIa64StMsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
//				case LocalSlotIndexFieldedLILStMsSymbol.PDB_ID:
//					symbol = new LocalSlotIndexFieldedLILStMsSymbol(pdb, reader);
//					break;
//				case ParameterSlotIndexFieldedLILStMsSymbol.PDB_ID:
//					symbol = new ParameterSlotIndexFieldedLILStMsSymbol(pdb, reader);
//					break;
//				case AnnotationMsSymbol.PDB_ID:
//					symbol = new AnnotationMsSymbol(pdb, reader);
//					break;
			case GlobalManagedProcedureStMsSymbol.PDB_ID:
				applier = new ManagedProcedureSymbolApplier(applicator,
					(AbstractManagedProcedureMsSymbol) symbol);
				break;
			case LocalManagedProcedureStMsSymbol.PDB_ID:
				applier = new ManagedProcedureSymbolApplier(applicator,
					(AbstractManagedProcedureMsSymbol) symbol);
				break;
//				case Reserved1MsSymbol.PDB_ID:
//					symbol = new Reserved1MsSymbol(pdb, reader);
//					break;
//				case Reserved2MsSymbol.PDB_ID:
//					symbol = new Reserved2MsSymbol(pdb, reader);
//					break;
//				case Reserved3MsSymbol.PDB_ID:
//					symbol = new Reserved3MsSymbol(pdb, reader);
//					break;
//				case Reserved4MsSymbol.PDB_ID:
//					symbol = new Reserved4MsSymbol(pdb, reader);
//					break;
			case LocalManagedDataStMsSymbol.PDB_ID:
				applier = new DataSymbolApplier(applicator, (AbstractDataMsSymbol) symbol);
				break;
			case GlobalManagedDataStMsSymbol.PDB_ID:
				applier = new DataSymbolApplier(applicator, (AbstractDataMsSymbol) symbol);
				break;
//				case ManLocOrParamReltoVFPStMsSymbol.PDB_ID:
//					symbol = new ManLocOrParamReltoVFPStMsSymbol(pdb, reader);
//					break;
//				case ManagedLocalOrParameterSIRStMsSymbol.PDB_ID:
//					symbol = new ManagedLocalOrParameterSIRStMsSymbol(pdb, reader);
//					break;
			case ManagedSymbolWithSlotIndexFieldStMsSymbol.PDB_ID:
				applier = new ManagedSymbolWithSlotSymbolApplier(applicator,
					(AbstractManagedSymbolWithSlotIndexFieldMsSymbol) symbol);
				break;
//				case ManagedLocalOrParameterSIMRStMsSymbol.PDB_ID:
//					symbol = new ManagedLocalOrParameterSIMRStMsSymbol(pdb, reader);
//					break;
//				case ManLocOrParamReltoAMPStMsSymbol.PDB_ID:
//					symbol = new ManLocOrParamReltoAMPStMsSymbol(pdb, reader);
//					break;
//				case ManagedLocalOrParameterSIMR2StMsSymbol.PDB_ID:
//					symbol = new ManagedLocalOrParameterSIMR2StMsSymbol(pdb, reader);
//					break;
//				case IndexForTypeReferencedByNameFromMetadataMsSymbol.PDB_ID:
//					symbol = new IndexForTypeReferencedByNameFromMetadataMsSymbol(pdb, reader);
//					break;
			case UsingNamespaceStMsSymbol.PDB_ID:
				applier = new UsingNamespaceSymbolApplier(applicator,
					(AbstractUsingNamespaceMsSymbol) symbol);
				break;
//
//				// 0x1100 block
//				case ObjectNameMsSymbol.PDB_ID:
//					symbol = new ObjectNameMsSymbol(pdb, reader);
//					break;
			case Thunk32MsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case Block32MsSymbol.PDB_ID:
				applier = new BlockSymbolApplier(applicator, (AbstractBlockMsSymbol) symbol);
				break;
			case With32MsSymbol.PDB_ID:
				applier = new WithSymbolApplier(applicator, (AbstractWithMsSymbol) symbol);
				break;
			case Label32MsSymbol.PDB_ID:
				applier = new LabelSymbolApplier(applicator, (AbstractLabelMsSymbol) symbol);
				break;
//				case RegisterMsSymbol.PDB_ID:
//					symbol = new RegisterMsSymbol(pdb, reader);
//					break;
//				case ConstantMsSymbol.PDB_ID:
//					symbol = new ConstantMsSymbol(pdb, reader);
//					break;
			case UserDefinedTypeMsSymbol.PDB_ID:
				applier =
					new TypedefSymbolApplier(applicator, (AbstractUserDefinedTypeMsSymbol) symbol);
				break;
			case CobolUserDefinedTypeMsSymbol.PDB_ID:
				applier =
					new TypedefSymbolApplier(applicator, (AbstractUserDefinedTypeMsSymbol) symbol);
				break;
//				case ManyRegisterVariableMsSymbol.PDB_ID:
//					symbol = new ManyRegisterVariableMsSymbol(pdb, reader);
//					break;
//				case BasePointerRelative32MsSymbol.PDB_ID:
//					symbol = new BasePointerRelative32MsSymbol(pdb, reader);
//					break;
			case LocalData32MsSymbol.PDB_ID:
				applier = new DataSymbolApplier(applicator, (AbstractDataMsSymbol) symbol);
				break;
			case GlobalData32MsSymbol.PDB_ID:
				applier = new DataSymbolApplier(applicator, (AbstractDataMsSymbol) symbol);
				break;
			case Public32MsSymbol.PDB_ID:
				applier = new PublicSymbolApplier(applicator, (AbstractPublicMsSymbol) symbol);
				break;
			case LocalProcedureStart32MsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case GlobalProcedureStart32MsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case RegisterRelativeAddress32MsSymbol.PDB_ID:
				applier = new RegisterRelativeSymbolApplier(applicator,
					(AbstractRegisterRelativeAddressMsSymbol) symbol);
				break;
//				case LocalThreadStorage32MsSymbol.PDB_ID:
//					symbol = new LocalThreadStorage32MsSymbol(pdb, reader);
//					break;
//				case GlobalThreadStorage32MsSymbol.PDB_ID:
//					symbol = new GlobalThreadStorage32MsSymbol(pdb, reader);
//					break;
			case LocalProcedureStartMipsMsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case GlobalProcedureStartMipsMsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
//				case Compile2MsSymbol.PDB_ID:
//					symbol = new Compile2MsSymbol(pdb, reader);
//					break;
//				case ManyRegisterVariable2MsSymbol.PDB_ID:
//					symbol = new ManyRegisterVariable2MsSymbol(pdb, reader);
//					break;
			case LocalProcedureStartIa64MsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case GlobalProcedureStartIa64MsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
//				case LocalSlotIndexFieldedLILMsSymbol.PDB_ID:
//					symbol = new LocalSlotIndexFieldedLILMsSymbol(pdb, reader);
//					break;
//				case ParameterSlotIndexFieldedLILMsSymbol.PDB_ID:
//					symbol = new ParameterSlotIndexFieldedLILMsSymbol(pdb, reader);
//					break;
			case LocalManagedDataMsSymbol.PDB_ID:
				applier = new DataSymbolApplier(applicator, (AbstractDataMsSymbol) symbol);
				break;
			case GlobalManagedDataMsSymbol.PDB_ID:
				applier = new DataSymbolApplier(applicator, (AbstractDataMsSymbol) symbol);
				break;
//				case ManLocOrParamReltoVFPMsSymbol.PDB_ID:
//					symbol = new ManLocOrParamReltoVFPMsSymbol(pdb, reader);
//					break;
//				case ManagedLocalOrParameterSIRMsSymbol.PDB_ID:
//					symbol = new ManagedLocalOrParameterSIRMsSymbol(pdb, reader);
//					break;
			case ManagedSymbolWithSlotIndexFieldMsSymbol.PDB_ID:
				applier = new ManagedSymbolWithSlotSymbolApplier(applicator,
					(AbstractManagedSymbolWithSlotIndexFieldMsSymbol) symbol);
				break;
//				case ManagedLocalOrParameterSIMRMsSymbol.PDB_ID:
//					symbol = new ManagedLocalOrParameterSIMRMsSymbol(pdb, reader);
//					break;
//				case ManLocOrParamReltoAMPMsSymbol.PDB_ID:
//					symbol = new ManLocOrParamReltoAMPMsSymbol(pdb, reader);
//					break;
//				case ManagedLocalOrParameterSIMR2MsSymbol.PDB_ID:
//					symbol = new ManagedLocalOrParameterSIMR2MsSymbol(pdb, reader);
//					break;
			case UsingNamespaceMsSymbol.PDB_ID:
				applier = new UsingNamespaceSymbolApplier(applicator,
					(AbstractUsingNamespaceMsSymbol) symbol);
				break;
			case ProcedureReferenceMsSymbol.PDB_ID:
				applier =
					new ReferenceSymbolApplier(applicator, (AbstractReferenceMsSymbol) symbol);
				break;
			case DataReferenceMsSymbol.PDB_ID:
				applier =
					new ReferenceSymbolApplier(applicator, (AbstractReferenceMsSymbol) symbol);
				break;
			case LocalProcedureReferenceMsSymbol.PDB_ID:
				applier =
					new ReferenceSymbolApplier(applicator, (AbstractReferenceMsSymbol) symbol);
				break;
			case AnnotationReferenceMsSymbol.PDB_ID:
				applier =
					new ReferenceSymbolApplier(applicator, (AbstractReferenceMsSymbol) symbol);
				break;
			case TokenReferenceToManagedProcedureMsSymbol.PDB_ID:
				applier =
					new ReferenceSymbolApplier(applicator, (AbstractReferenceMsSymbol) symbol);
				break;
			case GlobalManagedProcedureMsSymbol.PDB_ID:
				applier = new ManagedProcedureSymbolApplier(applicator,
					(AbstractManagedProcedureMsSymbol) symbol);
				break;
			case LocalManagedProcedureMsSymbol.PDB_ID:
				applier = new ManagedProcedureSymbolApplier(applicator,
					(AbstractManagedProcedureMsSymbol) symbol);
				break;
			case TrampolineMsSymbol.PDB_ID:
				applier = new TrampolineSymbolApplier(applicator, (TrampolineMsSymbol) symbol);
				break;
//				case ManagedConstantMsSymbol.PDB_ID:
//					symbol = new ManagedConstantMsSymbol(pdb, reader);
//					break;
//				case AttribLocOrParamReltoVFPMsSymbol.PDB_ID:
//					symbol = new AttribLocOrParamReltoVFPMsSymbol(pdb, reader);
//					break;
//				case AttributedLocalOrParameterSIRMsSymbol.PDB_ID:
//					symbol = new AttributedLocalOrParameterSIRMsSymbol(pdb, reader);
//					break;
//				case AttribLocOrParamReltoAMPMsSymbol.PDB_ID:
//					symbol = new AttribLocOrParamReltoAMPMsSymbol(pdb, reader);
//					break;
//				case AttributedLocalOrParameterSIMRMsSymbol.PDB_ID:
//					symbol = new AttributedLocalOrParameterSIMRMsSymbol(pdb, reader);
//					break;
			case SeparatedCodeFromCompilerSupportMsSymbol.PDB_ID:
				applier = new SeparatedCodeSymbolApplier(applicator,
					(SeparatedCodeFromCompilerSupportMsSymbol) symbol);
				break;
			case LocalSymbolInOptimizedCode2005MsSymbol.PDB_ID:
				applier = new LocalOptimizedSymbolApplier(applicator,
					(AbstractLocalSymbolInOptimizedCodeMsSymbol) symbol);
				break;
//				case DefinedSingleAddressRange2005MsSymbol.PDB_ID:
//					symbol = new DefinedSingleAddressRange2005MsSymbol(pdb, reader);
//					break;
//				case DefinedMultipleAddressRanges2005MsSymbol.PDB_ID:
//					symbol = new DefinedMultipleAddressRanges2005MsSymbol(pdb, reader);
//					break;
			case PeCoffSectionMsSymbol.PDB_ID:
				applier =
					new PeCoffSectionSymbolApplier(applicator, (PeCoffSectionMsSymbol) symbol);
				break;
			case PeCoffGroupMsSymbol.PDB_ID:
				applier = new PeCoffGroupSymbolApplier(applicator, (PeCoffGroupMsSymbol) symbol);
				break;
//				case ExportMsSymbol.PDB_ID:
//					symbol = new ExportMsSymbol(pdb, reader);
//					break;
//				case IndirectCallSiteInfoMsSymbol.PDB_ID:
//					symbol = new IndirectCallSiteInfoMsSymbol(pdb, reader);
//					break;
//				case FrameSecurityCookieMsSymbol.PDB_ID:
//					symbol = new FrameSecurityCookieMsSymbol(pdb, reader);
//					break;
//				case DiscardedByLinkMsSymbol.PDB_ID:
//					symbol = new DiscardedByLinkMsSymbol(pdb, reader);
//					break;
//				case Compile3MsSymbol.PDB_ID:
//					symbol = new Compile3MsSymbol(pdb, reader);
//					break;
//				case EnvironmentBlockMsSymbol.PDB_ID:
//					symbol = new EnvironmentBlockMsSymbol(pdb, reader);
//					break;
			case LocalSymbolInOptimizedCodeMsSymbol.PDB_ID:
				applier = new LocalOptimizedSymbolApplier(applicator,
					(AbstractLocalSymbolInOptimizedCodeMsSymbol) symbol);
				break;
			case DefinedSingleAddressRangeMsSymbol.PDB_ID:
				applier = new DefinedSingleAddressRangeSymbolApplier(applicator,
					(AbstractDefinedSingleAddressRangeMsSymbol) symbol);
				break;
			case SubfieldDARMsSymbol.PDB_ID:
				applier = new DefinedSingleAddressRangeSymbolApplier(applicator,
					(AbstractDefinedSingleAddressRangeMsSymbol) symbol);
				break;
			case EnregisteredSymbolDARMsSymbol.PDB_ID:
				applier = new DefinedSingleAddressRangeSymbolApplier(applicator,
					(AbstractDefinedSingleAddressRangeMsSymbol) symbol);
				break;
			case FramePointerRelativeDARMsSymbol.PDB_ID:
				applier = new DefinedSingleAddressRangeSymbolApplier(applicator,
					(AbstractDefinedSingleAddressRangeMsSymbol) symbol);
				break;
			case EnregisteredFieldOfSymbolDARMsSymbol.PDB_ID:
				applier = new DefinedSingleAddressRangeSymbolApplier(applicator,
					(AbstractDefinedSingleAddressRangeMsSymbol) symbol);
				break;
			case FramePointerRelativeFullScopeDARMsSymbol.PDB_ID:
				applier = new DefinedSingleAddressRangeSymbolApplier(applicator,
					(AbstractDefinedSingleAddressRangeMsSymbol) symbol);
				break;
			case EnregisteredSymbolRelativeDARMsSymbol.PDB_ID:
				applier = new DefinedSingleAddressRangeSymbolApplier(applicator,
					(AbstractDefinedSingleAddressRangeMsSymbol) symbol);
				break;
			case LocalProcedure32IdMsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case GlobalProcedure32IdMsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case LocalProcedureMipsIdMsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case GlobalProcedureMipsIdMsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case LocalProcedureIa64IdMsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case GlobalProcedureIa64IdMsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
//				case BuildInformationMsSymbol.PDB_ID:
//					symbol = new BuildInformationMsSymbol(pdb, reader);
//					break;
//				case InlinedFunctionCallsiteMsSymbol.PDB_ID:
//					symbol = new InlinedFunctionCallsiteMsSymbol(pdb, reader);
//					break;
//				case InlinedFunctionEndMsSymbol.PDB_ID:
//					symbol = new InlinedFunctionEndMsSymbol(pdb, reader);
//					break;
			case ProcedureIdEndMsSymbol.PDB_ID:
				applier =
					new ProcedureIdEndSymbolApplier(applicator, (ProcedureIdEndMsSymbol) symbol);
				break;
			case HighLevelShaderLanguageRegDimDARMsSymbol.PDB_ID:
				applier = new DefinedSingleAddressRangeSymbolApplier(applicator,
					(AbstractDefinedSingleAddressRangeMsSymbol) symbol);
				break;
//				case GlobalDataHLSLMsSymbol.PDB_ID:
//					symbol = new GlobalDataHLSLMsSymbol(pdb, reader);
//					break;
//				case LocalDataHLSLMsSymbol.PDB_ID:
//					symbol = new LocalDataHLSLMsSymbol(pdb, reader);
//					break;
//				case FileStaticMsSymbol.PDB_ID:
//					symbol = new FileStaticMsSymbol(pdb, reader);
//					break;
//				case LocalDeferredProcedureCallGroupSharedMsSymbol.PDB_ID:
//					symbol = new LocalDeferredProcedureCallGroupSharedMsSymbol(pdb, reader);
//					break;
			case LocalProcedureStart32DeferredProcedureCallMsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case LocalProcedure32DeferredProcedureCallIdMsSymbol.PDB_ID:
				applier = new FunctionSymbolApplier(applicator, (AbstractProcedureMsSymbol) symbol);
				break;
			case DeferredProcedureCallPointerTagRegDimDARMsSymbol.PDB_ID:
				applier = new DefinedSingleAddressRangeSymbolApplier(applicator,
					(AbstractDefinedSingleAddressRangeMsSymbol) symbol);
				break;
//				case DeferredProcedureCallPointerTagToSymbolRecordMapMsSymbol.PDB_ID:
//					symbol = new DeferredProcedureCallPointerTagToSymbolRecordMapMsSymbol(pdb, reader);
//					break;
//				case ArmSwitchTableMsSymbol.PDB_ID:
//					symbol = new ArmSwitchTableMsSymbol(pdb, reader);
//					break;
//				case CalleesMsSymbol.PDB_ID:
//					symbol = new CalleesMsSymbol(pdb, reader);
//					break;
//				case CallersMsSymbol.PDB_ID:
//					symbol = new CallersMsSymbol(pdb, reader);
//					break;
//				case ProfileGuidedOptimizationDataMsSymbol.PDB_ID:
//					symbol = new ProfileGuidedOptimizationDataMsSymbol(pdb, reader);
//					break;
//				case InlinedFunctionCallsiteExtendedMsSymbol.PDB_ID:
//					symbol = new InlinedFunctionCallsiteExtendedMsSymbol(pdb, reader);
//					break;
//				case HeapAllocationSiteMsSymbol.PDB_ID:
//					symbol = new HeapAllocationSiteMsSymbol(pdb, reader);
//					break;
//				case ModuleTypeReferenceMsSymbol.PDB_ID:
//					symbol = new ModuleTypeReferenceMsSymbol(pdb, reader);
//					break;
//				case MiniPdbReferenceMsSymbol.PDB_ID:
//					symbol = new MiniPdbReferenceMsSymbol(pdb, reader);
//					break;
//				case MapToMiniPdbMsSymbol.PDB_ID:
//					symbol = new MapToMiniPdbMsSymbol(pdb, reader);
//					break;
//				case GlobalDataHLSL32MsSymbol.PDB_ID:
//					symbol = new GlobalDataHLSL32MsSymbol(pdb, reader);
//					break;
//				case LocalDataHLSL32MsSymbol.PDB_ID:
//					symbol = new LocalDataHLSL32MsSymbol(pdb, reader);
//					break;
//				case GlobalDataHLSL32ExtMsSymbol.PDB_ID:
//					symbol = new GlobalDataHLSL32ExtMsSymbol(pdb, reader);
//					break;
//				case LocalDataHLSL32ExtMsSymbol.PDB_ID:
//					symbol = new LocalDataHLSL32ExtMsSymbol(pdb, reader);
//					break;
//				case UnknownX1166MsSymbol.PDB_ID:
//					// We have recently seen 1167 and 1168, which implies that 1166 must exist.
//					symbol = new UnknownX1166MsSymbol(pdb, reader);
//					break;
//				case UnknownX1167MsSymbol.PDB_ID:
//					// We have not investigated this type yet, but have seen it in VS2017 output.
//					symbol = new UnknownX1167MsSymbol(pdb, reader);
//					break;
//				case UnknownX1168MsSymbol.PDB_ID:
//					// We have not investigated this type yet, but have seen it in VS2017 output.
//					symbol = new UnknownX1168MsSymbol(pdb, reader);
//					break;
			default:
				// This should never happen (unless we missed something
				// or MSFT has added new in a version we do not handle.
				applier = new NoSymbolApplier(applicator, symbol);
				break;
		}
		return applier;
	}

}
